package racejournal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import racejournal.domain.Race;
import racejournal.domain.RaceResult;
import racejournal.domain.Rider;
import racejournal.service.RaceResultsService;
import racejournal.service.RaceService;
import racejournal.service.RiderService;

import java.util.List;

/**
 * Created by alaplante on 2/10/16.
 */
@RestController
@RequestMapping("/rest/result")
public class RaceResultController {
    private final static Logger logger = LoggerFactory.getLogger(RaceService.class);

    @Autowired
    RaceResultsService raceResultsService;

    @Autowired
    RiderService riderService;

    @RequestMapping(value = "rider/download/{riderId}", method = RequestMethod.GET, consumes = "application/json")
    public void download(@PathVariable Long riderId) {
        Rider rider = riderService.fetchRider(riderId);
        raceResultsService.downloadAndParseRaceResults(rider.getUsacNumber());
        List<RaceResult> raceResuls = raceResultsService.fetchResults(rider.getUsacNumber());
        for(RaceResult raceResult : raceResuls) {
            logger.info("Rider {} has result {} at race {}", new Object[] {rider.getFirstName(), raceResult.getRaceResultType(), raceResult.getRace()});
        }
    }

    @RequestMapping(value = "rider/results/{riderId}", method = RequestMethod.GET, produces = "application/json")
    public List<RaceResult> races(@PathVariable Long riderId) {
        Rider rider = riderService.fetchRider(riderId);
        List<RaceResult> raceResuls = raceResultsService.fetchResults(rider.getUsacNumber());
        return raceResuls;
    }

    // Create race result take both rider and race
}
