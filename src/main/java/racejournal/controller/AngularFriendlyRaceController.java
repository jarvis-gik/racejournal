package racejournal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import racejournal.domain.Race;
import racejournal.service.RaceService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alaplante on 2/23/16.
 */

// Test Angular resource stuff
/*
    router.Handle("/books", handler(listBooks)).Methods("GET")
	router.Handle("/books", handler(addBook)).Methods("POST")
	router.Handle("/books/{id}", handler(getBook)).Methods("GET")
	router.Handle("/books/{id}", handler(updateBook)).Methods("POST")
	router.Handle("/books/{id}", handler(removeBook)).Methods("DELETE")
*/
@RestController
@RequestMapping("/races")
public class AngularFriendlyRaceController {
    private final static Logger logger = LoggerFactory.getLogger(AngularFriendlyRaceController.class);

    @Autowired
    RaceService raceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Race> getRaces() {
        return raceService.fetchRaces();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void createRace(@RequestBody Race race) {
        logger.info("createRace {}", race);
        raceService.saveRaces(Arrays.asList(race));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Race getRace(@PathVariable Long id) {
        logger.info("getRace {}", id);
        return raceService.fetchRace(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = "application/json")
    public void updateRace(@PathVariable Long id, @RequestBody Race race) {
        logger.info("updateRace {}", race);
        race.setId(id);
        raceService.updateRaces(Arrays.asList(race));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteRace(@PathVariable Long id) {
        logger.info("deleteRace {}", id);
        raceService.deleteRace(id);
    }
}
