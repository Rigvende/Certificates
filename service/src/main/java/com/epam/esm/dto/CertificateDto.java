package com.epam.esm.dto;

import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
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

    @Positive(message = "Must be positive number")
    @Max(value = Long.MAX_VALUE, message = "Id is too big")
    private Long id;

    @Size(max = 255, message = "Max length is 255 symbols")
    private String name;

    @Size(max = 500, message = "Max length is 500 symbols")
    private String description;

    private BigDecimal price;

    @Max(value = Integer.MAX_VALUE, message = "Duration is too big")
    @Positive(message = "Must be positive number")
    private Integer duration;

    private String createDate;

    private String lastUpdateDate;

    private List<TagDto> tags;

}
