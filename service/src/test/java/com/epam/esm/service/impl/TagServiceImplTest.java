package com.epam.esm.service.impl;

import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagServiceImplTest {

    private static TagService tagServiceMock;
    private static TagService tagService;
    private static EmbeddedDatabase embeddedDatabase;

    @BeforeAll
    static void beforeAll() {
        tagServiceMock = Mockito.mock(TagServiceImpl.class);
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        TagRepository tagRepository = new TagRepository(jdbcTemplate, new TagMapper());
        TagDtoConverter tagDtoConverter = new TagDtoConverter();
        tagService = new TagServiceImpl(tagRepository, tagDtoConverter);
    }

    @Order(1)
    @Test
    void findById() {
        TagDto tagDto = new TagDto();
        Mockito.when(tagServiceMock.findById(1L)).thenReturn(tagDto);
        TagDto tag = tagService.findById(1L);
        assertNotNull(tag);
        assertEquals(tag.getName(), "spa");
    }

    @Order(2)
    @Test
    void findAll() {
        List<TagDto> tags = new ArrayList<>();
        Mockito.when(tagServiceMock.findAll()).thenReturn(tags);
        List<TagDto> dtos = tagService.findAll();
        assertEquals(dtos.size(), 4);
    }

    @Order(3)
    @Test
    void save() {
        TagDto tagDto = new TagDto(null, "summer", TagDto.TagStatus.NEW);
        tagService.save(tagDto);
        TagDto dto = tagService.findById(5L);
        assertNotNull(dto);
        assertEquals(dto.getName(), "summer");
        assertEquals(tagService.findAll().size(), 5);
    }

    @Order(4)
    @Test
    void delete() {
        tagService.delete(1L);
        Throwable thrown = assertThrows(ServiceException.class,
                () -> tagService.findById(1L));
        assertNotNull(thrown.getMessage());
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
