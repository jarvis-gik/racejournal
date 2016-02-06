package belllap.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by alaplante on 2/2/16.
 */
public class Race {
    // todo autoassign?
    Long id;

    String name;
    LocalDate date;
    String city;
    String state;
    RaceType raceType;

    public Race() {}

    public Race(String name, LocalDate date, String city, String state, RaceType raceType) {
        this.name = name;
        this.date = date;
        this.city = city;
        this.state = state;
        this.raceType = raceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO just return a simple mm/dd/yyyy and have ui pretty format it
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy", timezone="MST")
    public LocalDate getDate() {
        return date;
    }

    // todo date api conversions is nasty
    public Long getEpochDate() {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant).getTime();
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="EEEE, MMMM d", timezone="MST")
    public LocalDate getPrettyDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void setRaceType(RaceType raceType) {
        this.raceType = raceType;
    }

    @Override
    public String toString() {
        return String.format("Race[id=%s, name='%s', date=%s, city='%s', state='%s', raceType='%s']",
            id, name, date, city, state, raceType);
    }
}
