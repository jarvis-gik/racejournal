package belllap.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by alaplante on 2/6/16.
 */
public class DownloadParseRaceFile {
    AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) throws Exception {
        DownloadParseRaceFile instance = new DownloadParseRaceFile();
        instance.downloadParse();
    }

    public void downloadParse() throws Exception {
        URL url = new URL("http://www.coloradocycling.org/calendar/download");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = null;

        while ((line = reader.readLine()) != null) {
            String sqlLine = parseCsvLine(atomicLong, line);
            if(sqlLine != null)
                System.out.println(sqlLine);

        }
    }

    //   0            1                                          2      3         4       5
// 1853,"Lucky Pie Criterium -  CO Master Crit Championships",,08/21/2016,Louisville,CO,,,,Y,
    String parseCsvLine(AtomicLong atomicLong, String line) {
        if(line.contains("\"event number\"")) return null; // skip header
        if(line.contains("(SMP 1-2, SM 3, SW P-1-2)")) {
            line = line.replaceAll("SMP 1-2, SM 3, SW P-1-2", "SMP 1-2 SM 3 SW P-1-2");
        }

        String[] tokens = line.split(",");
        Long id= atomicLong.incrementAndGet();
        String name = tokens[1].replaceAll("\"","").trim().replaceAll("'", "").trim();
        String[] dateTokens = tokens[3].split("/");
        LocalDate date = LocalDate.of(Integer.parseInt(dateTokens[2]), Integer.parseInt(dateTokens[0]), Integer.parseInt(dateTokens[1]));
        String city = tokens[4].replaceAll("\"","").trim().replaceAll("'", "").trim();
        String state = tokens[5].replaceAll("\"","").trim().replaceAll("'", "").trim();
        String raceType = categorizeRaceType(name);

        return String.format("insert into races (id, name, date, city, state, race_type) values (%s, '%s', '%s', '%s', '%s', '%s')",
                id, name, date, city, state, raceType);

    }

    String categorizeRaceType(String name) {
        if(name.contains("Hill") || name.contains("HC")) {
            return "HILL_CLIMB";
        } else if(name.contains("TT") || name.contains("Time")) {
            return "TIME_TRIAL";
        } else if(name.contains("Crit")) {
            return "CRITERIUM";
        } else if(name.contains("Road") || name.contains("RR") || name.contains("Circuit")) {
            return "ROAD_RACE";
        }  else {
            return "OTHER";
        }
    }
}
