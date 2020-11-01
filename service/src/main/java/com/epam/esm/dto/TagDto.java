package com.epam.esm.dto;

import com.epam.esm.entity.impl.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO for {@link Tag} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private Long id;
    private String name;
    private TagStatus status;

    public enum TagStatus {
        NEW, DELETED, PRESENT
    }

}
