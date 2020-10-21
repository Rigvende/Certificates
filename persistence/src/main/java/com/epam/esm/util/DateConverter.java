package com.epam.esm.util;

import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for converting sql date to java date and back
 * @author Marianna Patrusova
 * @version 1.0
 */
@Component
public class DateConverter {

    /**
     * Method: convert sql timestamp value to local date and time
     * @return instance of {@link LocalDateTime}
     */
    public LocalDateTime convertDateFromSqlTimestamp(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    /**
     * Method: convert local date and time value to sql timestamp
     * @return instance of {@link Timestamp}
     */
    public Timestamp convertSqlTimestampFromDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

}
