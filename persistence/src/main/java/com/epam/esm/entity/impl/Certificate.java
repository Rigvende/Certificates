package com.epam.esm.entity.impl;

import com.epam.esm.entity.AbstractEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate extends AbstractEntity {

    private static final long serialVersionUID = -4899170986028012365L;
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;  //in days

}
