package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDtoConverter;
import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.CertificateService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    void findById() {
        CertificateDto certificate = new CertificateDto();
        Mockito.when(certificateServiceMock.findById(1L)).thenReturn(certificate);
        CertificateDto certificateDto = certificateService.findById(1L);
        assertNotNull(certificateDto);
        assertEquals(certificateDto.getName(), "Spa");
    }

    @Test
    @Order(2)
    void findAll() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAll()).thenReturn(certificates);
        List<CertificateDto> dtos = certificateService.findAll();
        assertEquals(dtos.size(), 3);
    }

    @Test
    @Order(8)
    void save() {
        CertificateDto certificateDto = new CertificateDto(null, "Massage", "new certificate",
                BigDecimal.valueOf(20.00), 5, null, null, null);
        certificateService.save(certificateDto);
        assertEquals(certificateService.findAll().size(), 4);
        assertNotNull(certificateService.findById(4L));
        assertEquals(certificateService.findAllByName("Massage").size(), 1);
    }

    @Test
    @Order(6)
    void update() {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setName("Yoga");
        certificateDto.setPrice(BigDecimal.valueOf(10.00));
        certificateService.update(1L, certificateDto);
        CertificateDto found = certificateService.findById(1L);
        assertEquals(found.getName(), "Yoga");
        assertNotEquals(found.getPrice(), BigDecimal.valueOf(15.00));
        assertEquals(found.getDuration(), 3);
    }

    @Order(9)
    @Test
    void delete() throws ServiceException {
        certificateService.delete(1L);
        Throwable thrown = assertThrows(ServiceException.class,
                () -> certificateService.findById(1L));
        assertNotNull(thrown.getMessage());
    }

    @Order(4)
    @Test
    void findAllByTag() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByTag("rest")).thenReturn(certificates);
        List<CertificateDto> dtos = certificateService.findAllByTag("rest");
        assertEquals(dtos.size(), 2);
    }

    @Order(5)
    @Test
    void findAllSorted() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllSorted("asc", "name")).thenReturn(certificates);
        List<CertificateDto> dtos = certificateService.findAllSorted("asc", "name");
        assertEquals(dtos.get(0).getName(), "Pizza-bar");
    }

    @Order(3)
    @Test
    void findAllByName() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByName("Spa")).thenReturn(certificates);
        List<CertificateDto> dtos = certificateService.findAllByName("S");
        assertEquals(dtos.size(), 2);
    }

    @Order(7)
    @Test
    void findAllByDescription() {
        List<CertificateDto> certificates = new ArrayList<>();
        Mockito.when(certificateServiceMock.findAllByDescription("Gift certificate")).thenReturn(certificates);
        List<CertificateDto> dtos = certificateService.findAllByDescription("ift");
        assertEquals(dtos.size(), 2);
    }

    @AfterAll
    static void tearDown() {
        embeddedDatabase.shutdown();
    }

}
