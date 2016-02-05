package belllap.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by alaplante on 2/2/16.
 */
public enum RaceType {
    CRITERIUM("Criterium"),
    ROAD_RACE("Road Race"),
    HILL_CLIMB("Hill Climb"),
    TIME_TRIAL("Time Trial"),
    OTHER("Other");

    private String value;

    RaceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
