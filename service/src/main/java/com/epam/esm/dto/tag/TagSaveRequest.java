package com.epam.esm.dto.tag;

import com.epam.esm.entity.impl.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagSaveRequest {

    private String name;

    public Tag toTag() {
        return Tag.builder().name(name).build();
    }

}
