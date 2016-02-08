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
    public List<Race> fetchRaces() {
        List<Race> races = (List<Race>) sessionFactory.getCurrentSession()
                .createCriteria(Race.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return races;
    }

    @Override
    @Transactional
    public void saveRaces(List<Race> races) {
        for(Race race : races) {
            logger.info("Attempt to save race {}", race.getName());
            sessionFactory.getCurrentSession().saveOrUpdate(race);
        }
    }
}
