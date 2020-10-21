package com.epam.esm.dto.certificate;

import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateSaveRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<Long> tagIds;

    public Certificate toCertificate() {
        return Certificate.builder()
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .createDate(LocalDateTime.now())
                .build();
    }

}