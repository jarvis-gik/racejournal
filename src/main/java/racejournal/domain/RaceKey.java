package racejournal.domain;

import java.time.LocalDate;

/**
 * Created by alaplante on 2/10/16.
 */
public class RaceKey {
    private String name;
    private LocalDate date;

    public RaceKey(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("RaceKey[name='%s', date=%s]", name, date);
    }
}
