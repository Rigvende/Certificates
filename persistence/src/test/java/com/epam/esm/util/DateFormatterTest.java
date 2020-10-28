package com.epam.esm.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static org.junit.jupiter.api.Assertions.*;

class DateFormatterTest {

    @Test
    void formatLocalDateTimeToOffsetTest() {
        LocalDateTime local = LocalDateTime.now();
        OffsetDateTime offset = DateFormatter.formatLocalDateTimeToOffset(local);
        ZoneOffset actual = offset.getOffset();
        ZoneOffset expected = ZoneOffset.ofHours(3);
        assertEquals(expected, actual);
    }

    @Test
    void formatOffsetDateTimeToLocalTest() {
        OffsetDateTime offset = OffsetDateTime
                .of(2020, 1, 12, 9, 10, 30, 3, ZoneOffset.ofHours(5));
        LocalDateTime actual = DateFormatter.formatOffsetDateTimeToLocal(offset);
        assertEquals(actual.getHour(), 9);
    }
}