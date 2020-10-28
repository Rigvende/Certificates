package com.epam.esm.util;

import java.time.*;

/**
 * Class for converting local date and time to ISO 8601 format and back
 * @author Marianna Patrusova
 * @version 1.0
 */
public final class DateFormatter {

    /**
     * Method: convert local date and time to ISO 8601 format
     * @param localDateTime: {@link LocalDateTime} instance
     * @return {@link OffsetDateTime} representation of date and time
     */
    public static OffsetDateTime formatDateToIso8601(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }

    /**
     * Method: convert ISO 8601 format to local date and time
     * @param offsetDateTime: {@link OffsetDateTime} instance
     * @return {@link LocalDateTime} representation of date and time
     */
    public static LocalDateTime formatDateToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toLocalDateTime();
    }

}
