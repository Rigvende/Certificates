package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CertificateDtoConverterTest {

    private static CertificateDtoConverter certificateDtoConverter;

    @BeforeAll
    static void beforeAll() {
        TagDtoConverter tagDtoConverter = new TagDtoConverter();
        certificateDtoConverter = new CertificateDtoConverter(tagDtoConverter);
    }

    @Test
    void toResponseDto() {
        Certificate certificate = new Certificate(1L, "Massage", "new certificate",
                BigDecimal.valueOf(20.00), OffsetDateTime.now(), null, 10, null);
        CertificateDto dto = certificateDtoConverter.toResponseDto(certificate);
        assertNotNull(dto);
        assertEquals(dto.getName(), "Massage");
    }

    @Test
    void toResponseDtoList() {
        List<Certificate> list = Arrays.asList(new Certificate(1L, "Massage", "new certificate",
                        BigDecimal.valueOf(20.00), OffsetDateTime.now(), null, 10, null),
                new Certificate(2L, "Swimming", "water certificate",
                        BigDecimal.valueOf(15), OffsetDateTime.now(), null, 5, null));
        List<CertificateDto> dtos = certificateDtoConverter.toResponseDtoList(list);
        assertTrue(dtos.size() > 0);
        assertEquals(dtos.get(1).getName(), "Swimming");
    }

    @Test
    void toNewCertificate() {
        CertificateDto dto = new CertificateDto(null, "Massage", "new certificate",
                BigDecimal.valueOf(20.00), 10, null, null, null);
        Certificate certificate = certificateDtoConverter.toNewCertificate(dto);
        assertEquals(certificate.getName(), "Massage");
        assertNotNull(certificate.getCreateDate());
    }

    @Test
    void toUpdatedCertificate() {
        Certificate old = new Certificate(1L, "Massage", "new certificate",
                BigDecimal.valueOf(20.00), OffsetDateTime.now(), null, 10, null);
        CertificateDto dto = new CertificateDto(null, "Yoga", null,
                BigDecimal.valueOf(15.00), null, null, null, null);
        Certificate updated = certificateDtoConverter.toUpdatedCertificate(old, dto);
        assertNotEquals(updated.getName(), "Massage");
        assertEquals(updated.getPrice(), BigDecimal.valueOf(15.00));
        assertNotNull(updated.getLastUpdateDate());
    }

}
