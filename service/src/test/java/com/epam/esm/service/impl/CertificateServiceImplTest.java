package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDtoConverter;
import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.CertificateService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CertificateServiceImplTest {

    private static CertificateService certificateServiceMock;
    private static CertificateService certificateService;
    private static EmbeddedDatabase embeddedDatabase;

    @BeforeAll
    static void beforeAll() {
        certificateServiceMock = Mockito.mock(CertificateServiceImpl.class);
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        CertificateRepository certificateRepository = new CertificateRepository(new CertificateMapper(), jdbcTemplate);
        TagRepository tagRepository = new TagRepository(jdbcTemplate, new TagMapper());
        TagDtoConverter tagDtoConverter = new TagDtoConverter();
        certificateService = new CertificateServiceImpl(certificateRepository,
                new CertificateDtoConverter(tagDtoConverter), tagRepository, tagDtoConverter);
    }

    @Test
    void findById() {
        CertificateDto certificate = new CertificateDto();
        Mockito.when(certificateServiceMock.findById(1L)).thenReturn(certificate);
        CertificateDto certificateDto = certificateService.findById(1L);
        assertNotNull(certificateDto);
        assertEquals(certificateDto.getName(), "Spa");
    }

    @Test
    void findAll() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAll()).thenReturn(certificates);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAllByTag() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByTag("rest")).thenReturn(certificates);
    }

    @Test
    void findAllSorted() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllSorted("asc", "name")).thenReturn(certificates);
    }

    @Test
    void findAllByName() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByName("Spa")).thenReturn(certificates);
    }

    @Test
    void findAllByDescription() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByDescription("Gift certificate")).thenReturn(certificates);
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
