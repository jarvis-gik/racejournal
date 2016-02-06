/**
 * Created by alaplante on 2/5/16.
 */
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;

LocalDate localDate = new LocalDate(2011, 01, 01);
Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
Date date = Date.from(instant);
println date
println date.getTime();