package com.epam.esm.dto.certificate;

import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    public static CertificateResponse toResponse(Certificate certificate) {
        CertificateResponse certificateResponse = new CertificateResponse();
        certificateResponse.setId(certificate.getId());
        certificateResponse.setName(certificate.getName());
        certificateResponse.setDescription(certificate.getDescription());
        certificateResponse.setPrice(certificate.getPrice());
        certificateResponse.setDuration(certificate.getDuration());
        return certificateResponse;
    }

    public static List<CertificateResponse> toResponseList(List<Certificate> certificates) {
        List<CertificateResponse> certificateResponses = new ArrayList<>();
        for (Certificate certificate: certificates) {
            certificateResponses.add(toResponse(certificate));
        }
        return certificateResponses;
    }

}
