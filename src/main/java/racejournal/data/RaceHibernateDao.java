package racejournal.data;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import racejournal.domain.Race;
import racejournal.domain.RaceResult;
import racejournal.domain.RaceResultType;
import racejournal.domain.Rider;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 */
@Primary
@Repository
@EnableTransactionManagement
public class RaceHibernateDao implements RaceDao {
    private final static Logger logger = LoggerFactory.getLogger(RaceHibernateDao.class);

    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        logger.info("SessionFactory set");
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Race fetchRace(Long id) {
        return sessionFactory.getCurrentSession().get(Race.class, id);
    }

    @Override
    @Transactional
    public List<Race> fetchRaces() {
        List<Race> races = (List<Race>) sessionFactory.getCurrentSession()
                .createCriteria(Race.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("fetchRaces return {} records", races.size());
        return races;
    }

    @Override
    @Transactional
    public void saveRaces(List<Race> races) {
        for(Race race : races) {
            logger.info("Save race {}", race.getName());
            sessionFactory.getCurrentSession().saveOrUpdate(race);
        }
    }

    @Override
    @Transactional
    public void updateRaces(List<Race> races) {
        for(Race race : races) {
            logger.info("Update race {} {}", race.getId(), race.getName());
//            Race persistantRace = fetchRace(race.getId());
            Race mergedRace = (Race) sessionFactory.getCurrentSession().merge(race);
            sessionFactory.getCurrentSession().saveOrUpdate(mergedRace);
        }
    }

    @Override
    @Transactional
    public void deleteRace(Long id) {
        sessionFactory.getCurrentSession().delete(fetchRace(id)); // not performant since we load the object but this would cascade delete if needed
    }

    @Override
    @Transactional
    public void testCreate() {

        Race race1 = fetchRace(10l);
        logger.info("Got race 1 {}", race1);

        Race race2 = fetchRace(20l);
        logger.info("Got race 2 {}", race2);

        Rider rider = new Rider();
        rider.setFirstName("Adam");
        rider.setLastName("LaPlante");
        rider.setCity("Broomfield");
        rider.setState("CO");
        rider.setUsacNumber(428596L);
        rider.setClub("Sonic Boom Racing");
        sessionFactory.getCurrentSession().saveOrUpdate(rider);

        RaceResult raceResult1 = new RaceResult();
        raceResult1.setPlacing(6l);
        raceResult1.setFieldSize(60l);
        raceResult1.setDescription("Fun race top 10");
        raceResult1.setRaceResultType(RaceResultType.TOP_TEN);
        raceResult1.setRider(rider);
        raceResult1.setRace(race1);
        sessionFactory.getCurrentSession().saveOrUpdate(raceResult1);
        race1.getRaceResults().add(raceResult1);
        sessionFactory.getCurrentSession().saveOrUpdate(race1);

        RaceResult raceResult2 = new RaceResult();
        raceResult2.setPlacing(32l);
        raceResult2.setFieldSize(45l);
        raceResult2.setDescription("Race sucked");
        raceResult2.setRaceResultType(RaceResultType.FINISH);
        raceResult2.setRider(rider);
        raceResult2.setRace(race2);
        sessionFactory.getCurrentSession().saveOrUpdate(raceResult2);
        race2.getRaceResults().add(raceResult2);
        sessionFactory.getCurrentSession().saveOrUpdate(race2);

        rider.getRaceResults().add(raceResult1);
        rider.getRaceResults().add(raceResult2);
        sessionFactory.getCurrentSession().saveOrUpdate(rider);

        logger.info("Done");
    }

    @Override
    @Transactional
    public void testGet() {
        List<Rider> riders = (List<Rider>) sessionFactory.getCurrentSession()
                .createCriteria(Rider.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for(Rider rider : riders) {
            logger.info("Found rider {}", rider);
//            logger.info("Rider has race results {}", rider.getRaceResults());
            for(RaceResult raceResult : rider.getRaceResults()) {
                logger.info("Race result has rider {} at race {}", raceResult.getRider(), raceResult.getRace());
            }
        }

        Race race1 = fetchRace(10l);
        logger.info("Got race  {} with results {}", race1, race1.getRaceResults());

        Race race2 = fetchRace(20l);
        logger.info("Got race 2 {} with results {}", race2, race2.getRaceResults());
    }
}
