package belllap.service;

import belllap.domain.RaceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import belllap.domain.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alaplante on 2/2/16.
 */
public class RaceService {
    private final static Logger logger = LoggerFactory.getLogger(RaceService.class);

    @Autowired
    private ResourceLoader resourceLoader;

    private String bootstrapFile;

    public void setBootstrapFile(String bootstrapFile) {
        this.bootstrapFile = bootstrapFile;
    }

    public List<Race> loadRaceData() {
        if(bootstrapFile == null) {
            logger.error("Bootstrap file null");
            return new ArrayList<Race>();
        }
        return parseCsv(bootstrapFile);
    }

    private List<Race> parseCsv(String file) {
        List<Race> races = new ArrayList<Race>();
        try {
            Resource resource = resourceLoader.getResource(String.format("classpath:%s", file));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            logger.info("Loaded file ", file);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                Race race = parseCsvLine(line);
                if(race!= null) races.add(race);
            }
        } catch(IOException e) {
            // log error
            logger.error("Error reading file", e);
        }
        return races;
    }

    //   0            1                                          2      3         4       5
    // 1853,"Lucky Pie Criterium -  CO Master Crit Championships",,08/21/2016,Louisville,CO,,,,Y,
    private Race parseCsvLine(String line) {
        String[] tokens = line.split(",");
        if(tokens.length != 10) {
            logger.error("Line is not 10 tokens {}", line);
            return null;
        }
        Race race = new Race();
        race.setName(tokens[1].replace("\"","").trim());
        String[] dateTokens = tokens[3].split("/");
        race.setDate(LocalDate.of(Integer.parseInt(dateTokens[2]), Integer.parseInt(dateTokens[0]), Integer.parseInt(dateTokens[1])));
        race.setCity(tokens[4].trim());
        race.setState(tokens[5].trim());
        race.setRaceType(categorizeRaceType(race.getName()));
        return race;
    }

    private RaceType categorizeRaceType(String name) {
//        name = name.toLowerCase();
        if(name.contains("Hill") || name.contains("HC")) {
            return RaceType.HILL_CLIMB;
        } else if(name.contains("TT") || name.contains("Time")) {
            return RaceType.TIME_TRIAL;
        } else if(name.contains("Crit")) {
            return RaceType.CRITERIUM;
        } else if(name.contains("Road") || name.contains("RR") || name.contains("Circuit")) {
            return RaceType.ROAD_RACE;
        }  else {
            logger.info("Could not categorize race type for {}", name);
            return RaceType.OTHER;
        }
    }

    public Map<RaceType, List<Race>> partitionRacesByRaceType(List<Race> races) {
        Map<RaceType, List<Race>> map = new HashMap<RaceType, List<Race>>();
        for(Race race : races) {
            if(map.get(race.getRaceType()) == null) {
                map.put(race.getRaceType(), new ArrayList<Race>());
            }
            map.get(race.getRaceType()).add(race);
        }
        return map;
    }
}

