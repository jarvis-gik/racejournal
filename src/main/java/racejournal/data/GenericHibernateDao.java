package racejournal.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableTransactionManagement
public class GenericHibernateDao {
    private final static Logger logger = LoggerFactory.getLogger(GenericHibernateDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public <T> T save(final T o) {
        logger.info("Save {}", o);
        return (T) sessionFactory.getCurrentSession().save(o);
    }

    @Transactional
    public void delete(final Object o) {
        logger.info("Delete {}", o);
        sessionFactory.getCurrentSession().delete(o);
    }

    /***/
    @Transactional
    public <T> T get(final Class<T> type, final Long id) {
        logger.info("Get {} {}", type, id);
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    /***/
    @Transactional
    public <T> T merge(final T o) {
        logger.info("Merge {}", o);
        return (T) sessionFactory.getCurrentSession().merge(o);
    }

    /***/
    @Transactional
    public <T> void saveOrUpdate(final T o) {
        logger.info("SaveOrUpdate {}", o);
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    @Transactional
    public <T> List<T> getAll(final Class<T> type) {
        logger.info("GetAll {}", type);
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }
}