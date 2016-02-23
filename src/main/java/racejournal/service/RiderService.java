package racejournal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import racejournal.data.GenericHibernateDao;
import racejournal.domain.Rider;

import java.util.List;

/**
 * Created by alaplante on 2/10/16.
 */
public class RiderService {
    private final static Logger logger = LoggerFactory.getLogger(RiderService.class);

    @Autowired
    GenericHibernateDao genericHibernateDao;

    public void saveRider(Rider rider) {
        genericHibernateDao.save(rider);
    }

    public Rider fetchRider(Long id) {
        return genericHibernateDao.get(Rider.class, id);
    }

    public List<Rider> fetchRiders() {
        return genericHibernateDao.getAll(Rider.class);
    }
}
