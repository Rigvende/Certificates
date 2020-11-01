package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FieldsValidatorTest {

    @Test
    void validateCertificate() {
        CertificateDto certificateDto = new CertificateDto(null, "Massage", "new certificate",
                BigDecimal.valueOf(20.00), -5, null, null, null);
        List<ErrorField> errors = FieldsValidator.validateCertificate(certificateDto);
        assertFalse(errors.isEmpty());
        assertEquals(errors.get(0).getField(),"duration");
    }

    @Test
    void validateTag() {
        TagDto tagDto = new TagDto(-1L, "123456789012345678901234567890", TagDto.TagStatus.NEW);
        List<ErrorField> errors = FieldsValidator.validateTag(tagDto);
        assertEquals(errors.size(), 2);
        assertEquals(errors.get(0).getField(), "id");
    }
}