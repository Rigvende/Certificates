package com.epam.esm.util;

import com.epam.esm.entity.impl.Certificate;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;
class NotNullFieldConsumerTest {

    @Test
    void changeIfPresent() {

        Certificate expected = new Certificate(1L, "SPA", "gift certificate",
                BigDecimal.valueOf(15.00), OffsetDateTime.now(), null, 3, null);

        Certificate updates = new Certificate(null, "SKATING", null, null,
                null, null, null, null);

        NotNullFieldConsumer.changeIfPresent(updates.getName(), expected::setName);
        NotNullFieldConsumer.changeIfPresent(updates.getDescription(), expected::setDescription);

        assertEquals(expected.getName(), "SKATING");
        assertNotNull(expected.getDescription());
    }

}
