package racejournal.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by alaplante on 2/9/16.
 */

// Couldnt find the original 2015 csv but found a website list...
// Make adhere to the 2016 style
// "event number",name,description,date,city,state,url,pdf,permits,champs,competitions

public class Races2015Parser {

    AtomicLong counter = new AtomicLong();
    private String file = "races2015.txt";

    public static void main(String[] args) throws Exception {
        (new Races2015Parser()).doit();
    }

    private void doit() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(file));
        for(String line : lines) {
//            System.out.println(line);
            String[] tokens = line.split(" : ");
            String dateString = tokens[0].trim();
            DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
            Date date = formatter.parse(dateString);
//            System.out.println("Date " + date.toString());
            // Need this: 04/29/2016
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDateStr = dateFormat.format(formatter.parse(dateString));


            String name = tokens[1].trim();
            name = name.replaceAll("Website", "").replaceAll(",", "").trim();

            StringBuffer buf = new StringBuffer();
            buf.append(counter.incrementAndGet()).append(",").append(name).append(",,").append(formattedDateStr).append(",").append("Somewhere,CO,")
                    .append(",,,,");


            System.out.println(buf);
        }

    }
}
