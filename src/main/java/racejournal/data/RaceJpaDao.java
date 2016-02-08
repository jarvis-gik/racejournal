package racejournal.data;

import org.springframework.stereotype.Repository;
import racejournal.domain.Race;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by alaplante on 2/8/16.
 */
@Repository
public class RaceJpaDao implements RaceDao {
    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public List<Race> fetchRaces() {
        return null;
    }

    @Override
    public void saveRaces(List<Race> races) {

    }
}
