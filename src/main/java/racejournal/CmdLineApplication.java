package racejournal;

import racejournal.domain.RaceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import racejournal.config.AppConfig;
import racejournal.domain.Race;
import racejournal.service.RaceService;

import java.util.List;
import java.util.Map;

/**
 * Created by alaplante on 2/2/16.
 */
public class CmdLineApplication {
    static Logger logger = LoggerFactory.getLogger(CmdLineApplication.class);
    public static void main(String[] args) {
        logger.info("Run CmdLineApplication...");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        RaceService raceService = applicationContext.getBean(RaceService.class);
        List<Race> races = raceService.fetchRaces();
        logger.info("Loaded {} races", races.size());

//        Map<RaceType, List<Race>> racesByRaceTypeMap = raceService.partitionRacesByRaceType(races);
//        for(RaceType raceType : racesByRaceTypeMap.keySet()) {
//            List<Race> racesByRaceType = racesByRaceTypeMap.get(raceType);
//            logger.info("--- {} {} ---", racesByRaceType.size(), raceType);
//            for(Race race : racesByRaceType) {
//                logger.info("   {}", race);
//            }
//        }
    }
}
