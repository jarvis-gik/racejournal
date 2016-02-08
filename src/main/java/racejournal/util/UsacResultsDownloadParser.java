package racejournal.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by alaplante on 2/6/16.
 */
public class UsacResultsDownloadParser {
    public static void main(String[] args) throws Exception {
        // https://www.usacycling.org/results/?compid=428596

        URL url = new URL("https://www.usacycling.org/results/?compid=428596");
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
                Double place = -1d;
                Double total = -1d;
                try {
                    place = Double.parseDouble(places[0].trim());
                    total = Double.parseDouble(places[1].trim());
                } catch(Exception e) { // ignore

                }
                Double percentage = 0d;
                if(place != -1) {
//                    System.out.println(place + "/" + total);
                    percentage = (place / total) * 100;
                }

                // todo qualify top 3 as podium, 1 as win, and top 10
                System.out.println(name + " " + date + " " + placing + " " + percentage.intValue() + "%");
            }
        }
    }

    public static String extractName(String line) {
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

    public static String extractDate(String line) {
//        System.out.println("Date length is " + line.length());
        int hyphenIndex = line.indexOf("-");
        line = line.substring(hyphenIndex - 11, hyphenIndex - 1);
        return line;
    }

    public static String extractPlacing(String line) {
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
}
