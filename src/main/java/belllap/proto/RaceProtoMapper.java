package belllap.proto;

import belllap.domain.Race;
import belllap.domain.RaceType;

import java.time.LocalDate;

/**
 * Created by alaplante on 2/3/16.
 */
public class RaceProtoMapper {
    public static RaceDirectoryProto.Race mapTo(Race race) {
        RaceDirectoryProto.Race.Builder raceBuilder = RaceDirectoryProto.Race.newBuilder();
        raceBuilder.setId(race.getId());
        raceBuilder.setName(race.getName());
        raceBuilder.setDate(race.getDate().toEpochDay()); // todo check
        raceBuilder.setCity(race.getCity());
        raceBuilder.setState(race.getState());
        switch (race.getRaceType()) {
            case ROAD_RACE: raceBuilder.setRaceType(RaceDirectoryProto.Race.RaceType.ROAD_RACE); break;
            case CRITERIUM: raceBuilder.setRaceType(RaceDirectoryProto.Race.RaceType.CRITERIUM); break;
            case HILL_CLIMB: raceBuilder.setRaceType(RaceDirectoryProto.Race.RaceType.HILL_CLIMB); break;
            case TIME_TRIAL: raceBuilder.setRaceType(RaceDirectoryProto.Race.RaceType.TIME_TRIAL); break;
            case OTHER: raceBuilder.setRaceType(RaceDirectoryProto.Race.RaceType.OTHER); break;
        }
        return raceBuilder.build();
    }

    public static Race mapFrom(RaceDirectoryProto.Race raceProto) {
        Race race = new Race();
        race.setId(raceProto.getId());
        race.setName(raceProto.getName());
        race.setDate(LocalDate.ofEpochDay(raceProto.getDate()));
        race.setCity(raceProto.getCity());
        race.setState(raceProto.getState());
        switch (raceProto.getRaceType()) {
            case ROAD_RACE: race.setRaceType(RaceType.ROAD_RACE); break;
            case CRITERIUM: race.setRaceType(RaceType.CRITERIUM); break;
            case HILL_CLIMB: race.setRaceType(RaceType.HILL_CLIMB); break;
            case TIME_TRIAL: race.setRaceType(RaceType.TIME_TRIAL); break;
            case OTHER: race.setRaceType(RaceType.OTHER); break;
        }
        return race;
    }
}
