package com.epam.esm.dto;

import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class DTO for {@link Certificate} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto> tags;

}
