package racejournal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alaplante on 2/9/16.
 */
@Entity
@Table(name = "riders")
@SequenceGenerator(
        name = "RIDER_SEQ_GENERATOR",
        sequenceName = "RIDER_SEQ",
        initialValue = 1, allocationSize = 1)
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RIDER_SEQ_GENERATOR")
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String city;
    private String state;
    @Column(name = "usac_number")
    private Long usacNumber;
    private String club;

    @OneToMany(mappedBy = "rider", fetch = FetchType.EAGER)
    Set<RaceResult> raceResults = new HashSet<RaceResult>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Long getUsacNumber() {
        return usacNumber;
    }

    public void setUsacNumber(Long usacNumber) {
        this.usacNumber = usacNumber;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @JsonIgnore
    public Set<RaceResult> getRaceResults() {
        return raceResults;
    }

    public void setRaceResults(Set<RaceResult> raceResults) {
        this.raceResults = raceResults;
    }

    @Override
    public String toString() {
        return String.format("Rider[id=%s, firstName='%s', lastName=%s, city='%s', state='%s', usacNumber='%s', club='%s]",
                id, firstName, lastName, city, state, usacNumber, club);
    }
}
