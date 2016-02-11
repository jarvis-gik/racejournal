package racejournal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import racejournal.data.GenericHibernateDao;
import racejournal.domain.Race;
import racejournal.domain.RaceKey;
import racejournal.domain.RaceResult;
import racejournal.domain.Rider;
import racejournal.util.UsacResultsDownloadParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by alaplante on 2/10/16.
 */
public class RaceResultsService {
    private final static Logger logger = LoggerFactory.getLogger(RaceService.class);

    @Autowired
    UsacResultsDownloadParser usacResultsDownloadParser;

//    @Autowired
//    RaceService raceService;

    @Autowired
    GenericHibernateDao genericHibernateDao;

    public List<RaceResult> fetchResults(Long usacNumber) {
        List<RaceResult> raceResults = new ArrayList<RaceResult>();
        // TODO proper finder to query with usacNumber
        List<RaceResult> allRaceResults = genericHibernateDao.getAll(RaceResult.class);
        for(RaceResult raceResult : allRaceResults) {
            if(raceResult.getRider().getUsacNumber().equals(usacNumber)) {
                raceResults.add(raceResult);
            }
        }
        return raceResults;
    }

    // Call will persist
    public void downloadAndParseRaceResults(Long usacNumber) {
        Map<RaceKey, RaceResult> raceResultMap = usacResultsDownloadParser.downloadAndParse(usacNumber);
        for(RaceKey raceKey : raceResultMap.keySet()) {
            RaceResult raceResult = raceResultMap.get(raceKey);
            logger.info("For race {} result was {}", raceKey, raceResult);
        }
        // todo proper finder
        List<Rider> riders = genericHibernateDao.getAll(Rider.class);
        for(Rider rider : riders) {
            if(rider.getUsacNumber().equals(usacNumber)) {
                mapResultsToRaces(rider, raceResultMap);
                break;
            }
        }
    }

    private void mapResultsToRaces(Rider rider, Map<RaceKey, RaceResult> raceResultMap) {
        // TODO add query to get races by date
        List<Race> races = genericHibernateDao.getAll(Race.class);
        for(RaceKey raceKey : raceResultMap.keySet()) {
            RaceResult raceResult = raceResultMap.get(raceKey);
            List<Race> possibleMatches = new ArrayList<Race>();
            for(Race race : races) { // TODO poor performance nested looping investigate adding query to get by date and compare
                if(race.getDate().equals(raceKey.getDate())) {
                    possibleMatches.add(race);
                }
            }
            if(possibleMatches.size() == 1) {
                Race race = possibleMatches.get(0);

                // Set race on result
                raceResult.setRace(race);
                // Set rider
                raceResult.setRider(rider);
                // Convert transient raceresult to persistant
                genericHibernateDao.save(raceResult);

                // Add result to race collection
                race.getRaceResults().add(raceResult);
                genericHibernateDao.save(race);

                // Add result to rider collection
                rider.getRaceResults().add(raceResult);
                genericHibernateDao.save(rider);
                logger.info("Mapped rider {} to result {} at race {}", new Object[] {rider.getFirstName(), raceResult.getRaceResultType(), raceResult.getRace()});
            } else if(possibleMatches.size() > 1) {
                // Figure out based on name
                logger.info("TODO based on name match");
            } else {
                logger.info("No race found for race result {}", raceResult);
            }
        }
    }
}
