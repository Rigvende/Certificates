package com.epam.esm.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for converting sql date to java date and back
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DateConverter {

    /**
     * Method: convert sql timestamp value to local date and time
     * @return instance of {@link LocalDateTime}
     */
    public static LocalDateTime convertDateFromSqlTimestamp(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    /**
     * Method: convert local date and time value to sql timestamp
     * @return instance of {@link Timestamp}
     */
    public static Timestamp convertSqlTimestampFromDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

}
