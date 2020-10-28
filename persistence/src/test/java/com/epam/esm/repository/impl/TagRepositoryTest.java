package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.*;

class TagRepositoryTest {

    private static EmbeddedDatabase embeddedDatabase;
    private static TagRepository tagRepository;

    @BeforeAll
    static void beforeAll() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        tagRepository = new TagRepository(jdbcTemplate, new TagMapper());
    }

    @Test
    void save() {
        Tag tag = tagRepository.save(new Tag(null, "summer"));
        assertNotNull(tag);
        assertNotNull(tag.getId());
        assertEquals("summer", tag.getName());
        assertNotNull(tagRepository.findEntityById(tag.getId()));
    }

    @Test
    void update() throws UnsupportedOperationException {
        Throwable thrown = assertThrows(UnsupportedOperationException.class,
                () -> tagRepository.update(new Tag()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void findByName() {
        assertNotNull(tagRepository.findByName("spa"));
    }

    @Test
    void findAllByCertificate() {
        assertTrue(tagRepository.findAllByCertificate(1L).size() > 0);
        assertFalse(tagRepository.findAllByCertificate(42L).size() > 0);
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
