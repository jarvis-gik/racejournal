package racejournal.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by alaplante on 2/9/16.
 */
public enum RaceResultType {
    WIN("Win"),
    PODIUM("Podium"),
    TOP_TEN("Top Ten"),
    FINISH("Finish"),
    DID_NOT_FINISH("Did Not Finish"),
    DID_NOT_START("Did Not Start");

    private String value;

    RaceResultType(String value) {
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
