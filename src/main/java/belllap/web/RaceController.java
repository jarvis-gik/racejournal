package belllap.web;

import belllap.domain.Race;
import belllap.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alaplante on 2/3/16.
 */
@Controller
public class RaceController {

    @Autowired
    RaceService raceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        List<Race> races = raceService.loadRaceData();
        return races.get(0).getName();
    }
}
