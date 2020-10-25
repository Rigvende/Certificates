package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.util.NotNullFieldConsumer;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class-converter(mapper) from {@link Certificate}
 * to {@link CertificateDto} and vice versa
 * @author Marianna Patrusova
 * @version 1.0
 */
@Component
public class CertificateDtoConverter {

    /**
     * Method: convert certificate to its DTO
     * @param certificate: instance of {@link Certificate}
     * @return instance of {@link CertificateDto}
     */
    public CertificateDto toResponseDto(Certificate certificate) {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(certificate.getId());
        certificateDto.setName(certificate.getName());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setCreateDate(certificate.getCreateDate() == null ?
                null : certificate.getCreateDate().toString());
        certificateDto.setLastUpdateDate(certificate.getLastUpdateDate() == null ?
                null : certificate.getLastUpdateDate().toString());
        return certificateDto;
    }

    /**
     * Method: convert list of certificates to its DTO list
     * @param certificates: list of instances of {@link Certificate}
     * @return list of instances of {@link CertificateDto}
     */
    public List<CertificateDto> toResponseDtoList(List<Certificate> certificates) {
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (Certificate certificate: certificates) {
            certificateDtos.add(toResponseDto(certificate));
        }
        return certificateDtos;
    }

    /**
     * Method: convert certificate DTO to new certificate
     * @param certificateDto: instance of {@link CertificateDto}
     * @return instance of {@link Certificate}
     */
    public Certificate toNewCertificate(CertificateDto certificateDto) {
        return Certificate.builder()
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .createDate(OffsetDateTime.now())
                .build();
    }

    /**
     * Method: use certificate DTO to update fields of existing certificate
     * @param certificate: instance of {@link Certificate}
     * @param certificateDto: instance of {@link CertificateDto}
     * @return updated certificate
     */
    public Certificate toUpdatedCertificate(Certificate certificate, CertificateDto certificateDto) {
        NotNullFieldConsumer.changeIfPresent(certificateDto.getName(), certificate::setName);
        NotNullFieldConsumer.changeIfPresent(certificateDto.getDescription(), certificate::setDescription);
        NotNullFieldConsumer.changeIfPresent(certificateDto.getPrice(), certificate::setPrice);
        NotNullFieldConsumer.changeIfPresent(certificateDto.getDuration(), certificate::setDuration);
        certificate.setLastUpdateDate(OffsetDateTime.now());
        return certificate;
    }

}
