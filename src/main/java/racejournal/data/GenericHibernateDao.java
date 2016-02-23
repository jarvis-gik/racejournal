package racejournal.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    /*
    TODO If refactor and change object method names then callers with string method name will break
    Investigate a better way
     */
    @Transactional
    public <T> List<T> findByProperty(final Class<T> type, final String property, final Object value) {
        logger.info("FindByProperty {} {} {}", new Object[] {type, property, value});
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        crit.add(Restrictions.eq(property, value));
        return crit.list();
    }

    /*
    TODO proper exception
     */
    @Transactional
    public <T> T findOneByProperty(final Class<T> type, final String property, final Object value) throws Exception {
        logger.info("FindByProperty {} {} {}", new Object[] {type, property, value});
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        crit.add(Restrictions.eq(property, value));
        List<T> list = crit.list();
        if(list.size() != 1) {
            logger.info("Found zero or more than one {}", list);
            throw new Exception(String.format("Single unique result not found for %s %s %s", type, property, value));
        }
        return list.get(0);
    }
}