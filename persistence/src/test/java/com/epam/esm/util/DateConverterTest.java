package com.epam.esm.util;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {

    @Test
    void convertDateFromSqlTimestamp() {
        Timestamp timestamp = Timestamp.valueOf("2020-01-01 9:12:13");
        LocalDateTime actual = DateConverter.convertDateFromSqlTimestamp(timestamp);
        assertEquals(actual.getDayOfMonth(), 1);
    }

    @Test
    void convertSqlTimestampFromDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = DateConverter.convertSqlTimestampFromDate(dateTime);
        assertNotNull(timestamp);
    }

}
