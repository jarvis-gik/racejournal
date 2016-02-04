package belllap.web;

import belllap.domain.Race;
import belllap.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alaplante on 2/3/16.
 */
@RestController
public class RaceController {

    @Autowired
    RaceService raceService;

    @RequestMapping(value = "/race", method = RequestMethod.GET, produces = "application/json")
    public Race race(@RequestParam("name") String name) {
        List<Race> races = raceService.loadRaceData();
        for(Race race : races) {
            if(race.getName().toLowerCase().contains(name.toLowerCase())) return race;
        }
        return null;
    }

    @RequestMapping(value = "/races", method = RequestMethod.GET, produces = "application/json")
    public List<Race> races() {
        List<Race> races = raceService.loadRaceData();
        return races;
    }
}
