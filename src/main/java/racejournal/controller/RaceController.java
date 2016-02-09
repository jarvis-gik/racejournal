package racejournal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import racejournal.domain.Race;
import racejournal.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alaplante on 2/3/16.
 */
@RestController
@RequestMapping("/rest")
public class RaceController {
    private final static Logger logger = LoggerFactory.getLogger(RaceService.class);

    @Autowired
    RaceService raceService;

//    @Autowired
//    RaceRepository raceRepository;
//
//    @RequestMapping(value = "/email", method = RequestMethod.GET)
//    public String email(@RequestParam String name) {
//        return raceRepository.getEmailByName(name);
//    }

//    @RequestMapping(value = "/racesByType", method = RequestMethod.GET, produces = "application/json")
//    public List<Race> races(@RequestParam("type") String type) {
//        return raceService.getRacesByType(type.trim());
//    }

    /*

    Content-Type: application/json

    {
     "name":"Adams Big Race",
     "date":"05/14/2016",
     "city":"Broomfield",
     "state":"CO",
     "raceType":"Criterium"
    }
     */

    @RequestMapping(value = "race", method = RequestMethod.POST, consumes = "application/json")
    public void createRace(@RequestBody Race race) {
        logger.info("createRace {}", race);
        raceService.saveRaces(Arrays.asList(race));
    }

    @RequestMapping(value = "race/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void updateRace(@PathVariable Long id, @RequestBody Race race) {
        logger.info("updateRace {}", race);
        race.setId(id);
        raceService.updateRaces(Arrays.asList(race));
    }

    @RequestMapping(value = "race/{id}", method = RequestMethod.GET)
    public Race getRace(@PathVariable Long id) {
        logger.info("getRace {}", id);
        return raceService.fetchRace(id);
    }

    @RequestMapping(value = "race/{id}", method = RequestMethod.DELETE)
    public void deleteRace(@PathVariable Long id) {
        logger.info("deleteRace {}", id);
        raceService.deleteRace(id);
    }

    @RequestMapping(value = "races", method = RequestMethod.GET, produces = "application/json")
    public List<Race> races() {
        List<Race> races = raceService.fetchRaces();
        return races;
    }

    @RequestMapping(value = "bootstrap", method = RequestMethod.GET)
    public void bootstrap() {
        raceService.bootstrap();
    }
}
