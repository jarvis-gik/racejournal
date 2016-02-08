package racejournal.data;

import racejournal.domain.Race;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 */
public interface RaceDao {
    void setDataSource(DataSource dataSource);
    List<Race> fetchRaces();
    void saveRaces(List<Race> races);
}
