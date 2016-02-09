package racejournal.data;

import org.hibernate.SessionFactory;
import racejournal.domain.Race;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 */
public interface RaceDao {
    Race fetchRace(Long id);
    List<Race> fetchRaces();
    void saveRaces(List<Race> races);
    void updateRaces(List<Race> races);
    void deleteRace(Long id);

    public void testCreate();
    public void testGet();
}
