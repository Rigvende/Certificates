package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateDtoConverter {

    public CertificateDto toResponseDto(Certificate certificate) {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(certificate.getId());
        certificateDto.setName(certificate.getName());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setCreateDate(certificate.getCreateDate());
        certificateDto.setLastUpdateDate(certificate.getLastUpdateDate());
        return certificateDto;
    }

    public List<CertificateDto> toResponseDtoList(List<Certificate> certificates) {
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (Certificate certificate: certificates) {
            certificateDtos.add(toResponseDto(certificate));
        }
        return certificateDtos;
    }

    public Certificate toNewCertificate(CertificateDto certificateDto) {
        return Certificate.builder()
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .createDate(LocalDateTime.now())
                .build();
    }

    public Certificate toUpdatedCertificate(CertificateDto certificateDto) {
        return Certificate.builder()
                .id(certificateDto.getId())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .createDate(certificateDto.getCreateDate())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

}
