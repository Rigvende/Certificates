package com.epam.esm.entity.impl;

import com.epam.esm.entity.AbstractEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractEntity {

    private static final long serialVersionUID = -8308807335057955104L;
    private Long id;
    private String name;

}
