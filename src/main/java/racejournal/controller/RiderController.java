package racejournal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import racejournal.domain.Race;
import racejournal.domain.Rider;
import racejournal.service.RaceService;
import racejournal.service.RiderService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alaplante on 2/10/16.
 */
@RestController
@RequestMapping("/rest/rider")
public class RiderController {
    private final static Logger logger = LoggerFactory.getLogger(RiderController.class);

    @Autowired
    RiderService riderService;

    @RequestMapping(value = "rider", method = RequestMethod.POST, consumes = "application/json")
    public void createRider(@RequestBody Rider rider) {
        logger.info("createRider {}", rider);
        riderService.saveRider(rider);
    }

    @RequestMapping(value = "riders", method = RequestMethod.GET)
    public List<Rider> createRider() {
        logger.info("riders");
        return riderService.fetchRiders();
    }

    @RequestMapping(value = "rider/{id}", method = RequestMethod.GET)
    public Rider getRace(@PathVariable Long id) {
        logger.info("getRider {}", id);
        return riderService.fetchRider(id);
    }
}
