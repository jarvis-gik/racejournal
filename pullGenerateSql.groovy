
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by alaplante on 2/5/16.
 */
 AtomicLong atomicLong = new AtomicLong();

URL url = new URL("http://www.coloradocycling.org/calendar/download");
BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
String line;


File file = new File("test.sql")
file.write "/* Race data */\n"

while ((line = reader.readLine()) != null) {

    String sqlLine = parseCsvLine(atomicLong, line);
    if(sqlLine != null)
        file << (sqlLine + "\n");

}
reader.close();

println file.text

//   0            1                                          2      3         4       5
// 1853,"Lucky Pie Criterium -  CO Master Crit Championships",,08/21/2016,Louisville,CO,,,,Y,
String parseCsvLine(AtomicLong atomicLong, String line) {

    if(line.contains("(SMP 1-2, SM 3, SW P-1-2)")) {
//        println "Special case"
        line = line.replaceAll("SMP 1-2, SM 3, SW P-1-2", "SMP 1-2 SM 3 SW P-1-2");
//        println "line $line"
    } // special case


    String[] tokens = line.split(",");
    if(tokens[0].equals("\"event number\"")) return null; // skip header

    Long id= atomicLong.incrementAndGet();
    String name = tokens[1].replace("\"","").trim().replaceAll("'", "");
    String[] dateTokens = tokens[3].split("/");
    LocalDate date = LocalDate.of(Integer.parseInt(dateTokens[2]), Integer.parseInt(dateTokens[0]), Integer.parseInt(dateTokens[1]));
//    java.sql.Date sqlDate = java.sql.Date.valueOf(date);
    String city = tokens[4].trim();
    String state = tokens[5].trim();
    String raceType = categorizeRaceType(name);

    String sql = "insert into races (id, name, date, city, state, race_type) values ($id, '$name', '$date', '$city', '$state', '$raceType')";
    return sql;
}

String categorizeRaceType(String name) {
//        name = name.toLowerCase();
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