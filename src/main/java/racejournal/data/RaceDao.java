package racejournal.data;

import org.hibernate.SessionFactory;
import racejournal.domain.Race;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 */
public interface RaceDao {
    void setDataSource(DataSource dataSource);
    void setSessionFactory(SessionFactory sessionFactory);
    List<Race> fetchRaces();
    void saveRaces(List<Race> races);
}
