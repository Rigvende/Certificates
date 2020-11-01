package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for request DTO fields validation
 * @author Marianna Patrusova
 * @version 1.0
 */
public final class FieldsValidator {

    /**
     * Method: validates {@link CertificateDto} instance fields
     * @param dto: Certificate DTO object
     * @return list of {@link ErrorField} instances (if there are no errors - it's empty)
     */
    public static List<ErrorField> validateCertificate(CertificateDto dto) {
        List<ErrorField> errors = new ArrayList<>();
        if (dto != null) {
            if (validateId(dto.getId())) {
                errors.add(new ErrorField("id", "Certificate id must be positive number"));
            }
            if (!validateCertificateName(dto.getName())) {
                errors.add(new ErrorField("name", "Certificate name must be from 1 to 255 symbols"));
            }
            if (!validateCertificateDescription(dto.getDescription())) {
                errors.add(new ErrorField("description", "Certificate description must be from 1 to 500 symbols"));
            }
            if (!validateCertificatePrice(dto.getPrice())) {
                errors.add(new ErrorField("price", "Certificate price must be positive number"));
            }
            if (!validateCertificateDuration(dto.getDuration())) {
                errors.add(new ErrorField("duration", "Certificate duration must be positive number"));
            }
        }
        return errors;
    }

    /**
     * Method: validates {@link TagDto} instance fields
     * @param dto: Tag DTO object
     * @return list of {@link ErrorField} instances (if there are no errors - it's empty)
     */
    public static List<ErrorField> validateTag(TagDto dto) {
        List<ErrorField> errors = new ArrayList<>();
        if (dto != null) {
            if (validateId(dto.getId())) {
                errors.add(new ErrorField("id", "Tag id must be positive number"));
            }
            if (!validateTagName(dto.getName())) {
                errors.add(new ErrorField("name", "Tag name must be from 1 to 20 symbols"));
            }
        }
        return errors;
    }

    private static boolean validateId (Long id) {
        return id != null && id <= 0;
    }

    private static boolean validateCertificateName(String name) {
        return name == null || (!name.trim().isEmpty() && name.length() <= 255);
    }

    private static boolean validateCertificateDescription(String description) {
        return description == null || (!description.trim().isEmpty() && description.length() <= 500);
    }

    private static boolean validateCertificatePrice(BigDecimal price) {
        return price == null || price.compareTo(BigDecimal.ZERO) > 0;
    }

    private static boolean validateCertificateDuration(Integer duration) {
        return duration == null || duration > 0;
    }

    private static boolean validateTagName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 20;
    }

}
