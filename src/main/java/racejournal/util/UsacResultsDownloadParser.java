package racejournal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import racejournal.domain.RaceKey;
import racejournal.domain.RaceResult;
import racejournal.domain.RaceResultType;
import racejournal.service.RaceService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alaplante on 2/6/16.
 */
public class UsacResultsDownloadParser {
    private final static Logger logger = LoggerFactory.getLogger(UsacResultsDownloadParser.class);

    // Return map of race name to result
    // Name may be used to map to an actual race if date is ambigous (more than 1 race per day)
    public Map<RaceKey, RaceResult> downloadAndParse(Long usacNumber) {
        // https://www.usacycling.org/results/?compid=428596

        Map<RaceKey, RaceResult> raceResultMap = new HashMap<RaceKey, RaceResult>();
        String stringUrl = String.format("https://www.usacycling.org/results/?compid=%s", usacNumber);
        try {
            URL url = new URL(stringUrl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;

            int count = 0;
            boolean startCaring = false;
            String name = null;
            String date = null;
            String placing = null;
            while ((line = reader.readLine()) != null) {
                if(line.contains("<span class='homearticleheader'>")) {
                    startCaring = true;

                }
                if(startCaring) {
                    if(count == 0) {
    //                    System.out.println("Name is " + extractName(line));
    //                    System.out.println("Date is " + extractDate(line));
                        name = extractName(line);
                        date = extractDate(line);
                    }
                    if(count == 3) {
    //                    System.out.println("Placing is " + extractPlacing(line));
                        placing = extractPlacing(line);
                    }
    //                System.out.println(line);
                    count++;


                }
                if(count == 4) {
                    // reset
                    count = 0;
                    startCaring = false;
                    String[] places = placing.split("/");
                    Double place = null; // new BigDecimal(-1);
                    Double total = null; // new BigDecimal(-1);
                    try {
                        place = new Double(places[0].trim());
                    } catch(Exception e) { // will happen on dnf / dnp ... Not good to use exception handling for logic but whatevs
                        logger.info("Cant format place {}", places[0]);
                    }
                    try {
                        total = new Double(places[1].trim());
                    } catch(Exception e) {
                        logger.info("Cant format total {}", places[1]);
                    }
                    Double percentage = null;
                    RaceResultType raceResultType = RaceResultType.DID_NOT_FINISH;
                    if(place != null) {
    //                    System.out.println(place + "/" + total);
                        percentage = place / total * 100;
                        raceResultType = categorize(place);
                    }
//                    logger.info(name + " " + date + " " + placing + " " + (percentage != null ? percentage.intValue() + "%" : "") + raceResultType );

                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Date actualDate = formatter.parse(date);
                    LocalDate localDate = actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    RaceResult raceResult = new RaceResult();
                    if(place != null) raceResult.setPlacing(place.longValue());
                    if(total != null) raceResult.setFieldSize(total.longValue());
                    if(percentage != null) raceResult.setPercentile(percentage.intValue());
                    raceResult.setRaceResultType(raceResultType);
                    raceResult.setDescription("Data pulled from USAC");
                    raceResultMap.put(new RaceKey(name, localDate), raceResult);
                }
            }
        } catch (MalformedURLException e) {
            logger.error("Error accessing USAC", e);
        } catch(Exception e) {
            logger.error("Error", e);
        }
        return raceResultMap;
    }

//    public static void main(String[] args) throws Exception {
//
//    }

    private String extractName(String line) {
        int linkIndex = line.indexOf("</a>");
        int permitIndex = line.indexOf("permit=");
//        char[] chars = line.toCharArray();
//        for(int i = permitIndex; ; i++) {
//            if(chars[i] == '>') {
//                break;
//            }
//        }
        line = line.substring(permitIndex, linkIndex);
        int nameStart = line.indexOf(">");
        line = line.substring(nameStart + 1, line.length());
        return line;
    }

    private String extractDate(String line) {
//        System.out.println("Date length is " + line.length());
        int hyphenIndex = line.indexOf("-");
        line = line.substring(hyphenIndex - 11, hyphenIndex - 1);
        return line;
    }

    private String extractPlacing(String line) {
//        System.out.println("Placing length is " + line.length());
        int slashIndex = line.indexOf("/");
        line = line.substring(slashIndex - 4, slashIndex + 4);
        line = line.replace("d>", "");
        line = line.replace(">", "");
//        if(line.contains("DNP") || line.contains("DNF")) {
//            return "Did not place";
//        }
        return line;
    }

    private RaceResultType categorize(Double place) {
        if(place == 1) {
            return RaceResultType.WIN;
        } else if(place <= 3) {
            return RaceResultType.PODIUM;
        } else if(place <= 10) {
            return RaceResultType.TOP_TEN;
        } else {
            return RaceResultType.FINISH;
        }
    }
}
