package com.epam.esm.dto.certificate;

import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateUpdateRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    public Certificate toCertificate() {
        return Certificate.builder()
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

}
