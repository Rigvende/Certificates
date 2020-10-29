package com.epam.esm.repository;

import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AbstractRepositoryTest {

    private static EmbeddedDatabase embeddedDatabase;
    private static TagRepository tagRepository;
    private static CertificateRepository certificateRepository;

    @BeforeAll
    static void beforeAll() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        tagRepository = new TagRepository(jdbcTemplate, new TagMapper());
        certificateRepository = new CertificateRepository(new CertificateMapper(), jdbcTemplate);
    }

    @Order(1)
    @Test
    void findAll() {
        assertTrue(tagRepository.findAll().size() > 0);
        assertNotEquals(tagRepository.findAll().get(0).getName(), "horror");
        assertTrue(certificateRepository.findAll().size() > 0);
        assertEquals(certificateRepository.findAll().get(0).getName(), "Spa");
    }

    @Order(2)
    @Test
    void findAllSorted() {
        assertEquals(certificateRepository
                .findAllSorted("asc", "name").get(0).getName(), "Pizza-bar");
        assertNotEquals(certificateRepository
                .findAllSorted("desc", "name").get(0).getName(), "Pizza-bar");
    }

    @Order(3)
    @Test
    void findEntityById() {
        assertNotNull(certificateRepository.findEntityById(1L));
        assertEquals(certificateRepository.findEntityById(1L).getName(), "Spa");
        assertNotNull(tagRepository.findEntityById(2L));
        assertEquals(tagRepository.findEntityById(2L).getName(), "rest");
    }

    @Order(4)
    @Test
    void delete() {
        assertTrue(tagRepository.delete(1L));
        assertTrue(certificateRepository.delete(2L));
        assertFalse(tagRepository.delete(5L));
        assertFalse(certificateRepository.delete(2L));
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
