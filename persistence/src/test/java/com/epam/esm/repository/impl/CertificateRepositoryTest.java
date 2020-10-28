package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CertificateRepositoryTest {

    private static EmbeddedDatabase embeddedDatabase;
    private static CertificateRepository certificateRepository;

    @BeforeAll
    static void beforeAll() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        certificateRepository = new CertificateRepository(new CertificateMapper(), jdbcTemplate);
    }

    @Test
    void save() {
        Certificate certificate = certificateRepository.save(new Certificate(null, "Skiing",
                "Some description", BigDecimal.valueOf(20.00),  OffsetDateTime.now(),
                null, 5, null));
        assertNotNull(certificate);
        assertNotNull(certificate.getId());
        assertEquals("Skiing", certificate.getName());
        assertNotNull(certificateRepository.findEntityById(certificate.getId()));
    }

    @Test
    void update() {
        Certificate certificate = certificateRepository.update(new Certificate(1L, "Spa",
                "Some description", BigDecimal.valueOf(20.00),  null,
                OffsetDateTime.now(), 5, null));
        assertNotNull(certificate);
        assertEquals(BigDecimal.valueOf(20.00), certificate.getPrice());
        assertNotNull(certificate.getLastUpdateDate());
    }

    @Test
    void saveCertificateTagLink() {
        certificateRepository.saveCertificateTagLink(1L, 4L);
        assertEquals(certificateRepository.findAllByTag("food").size(), 2);
    }

    @Test
    void deleteCertificateTagLink() {
        certificateRepository.deleteCertificateTagLink(1L, 1L);
        assertEquals(certificateRepository.findAllByTag("spa").size(), 0);
    }

    @Test
    void findAllByTag() {
        assertTrue(certificateRepository.findAllByTag("winter").size() > 0);
        assertFalse(certificateRepository.findAllByTag("summer").size() > 0);
    }

    @Test
    void findAllByName() {
        assertTrue(certificateRepository.findAllByName("bar").size() > 0);
        assertFalse(certificateRepository.findAllByDescription("rest").size() > 0);
    }

    @Test
    void findAllByDescription() {
        assertTrue(certificateRepository.findAllByDescription("certifi").size() > 0);
        assertFalse(certificateRepository.findAllByDescription("rest").size() > 0);
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
