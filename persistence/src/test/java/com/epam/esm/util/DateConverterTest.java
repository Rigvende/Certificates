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
        assertEquals(actual.getHour(), 9);
    }

    @Test
    void convertSqlTimestampFromDate() {
        LocalDateTime dateTimeOne = LocalDateTime.now();
        LocalDateTime dateTimeTwo = LocalDateTime.now();
        Timestamp timestampOne = DateConverter.convertSqlTimestampFromDate(dateTimeOne);
        Timestamp timestampTwo = DateConverter.convertSqlTimestampFromDate(dateTimeTwo);
        assertTrue(timestampOne.before(timestampTwo));
    }

}
