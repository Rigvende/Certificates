package com.epam.esm.dto;

import com.epam.esm.dto.validation.EnumNamePattern;
import com.epam.esm.entity.impl.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

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

    @Positive(message = "Must be positive number")
    @Max(value = Long.MAX_VALUE, message = "Id is too big")
    private Long id;

    @Size(max = 20, message = "Max length is 20 symbols")
    @NotBlank
    private String name;

    @EnumNamePattern(regexp = "NEW|PRESENT|DELETED",
            message = "Type must be one of TagStatus types")
    @NotNull(message = "Tag status is mandatory")
    private TagStatus status;

    public enum TagStatus {
        NEW, DELETED, PRESENT
    }

}
