package belllap.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

    public LocalDate getDate() {
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
