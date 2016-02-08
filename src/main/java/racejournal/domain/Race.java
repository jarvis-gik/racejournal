package racejournal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by alaplante on 2/2/16.
 */
@Entity
@Table(name = "races")
// http://izeye.blogspot.com/2015/08/error-sequence-hibernatesequence-not.html
@SequenceGenerator(
        name = "RACE_SEQ_GENERATOR",
        sequenceName = "RACE_SEQ",
        initialValue = 1, allocationSize = 1)
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RACE_SEQ_GENERATOR")
    @Column(name = "id")
    Long id;

    String name;
    LocalDate date;
    String city;
    String state;

    @Column(name = "race_type")
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
