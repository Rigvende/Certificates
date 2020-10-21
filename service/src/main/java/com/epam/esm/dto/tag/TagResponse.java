package com.epam.esm.dto.tag;

import com.epam.esm.entity.impl.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {

    private Long id;
    private String name;

    public static TagResponse toResponse(Tag tag) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setId(tag.getId());
        tagResponse.setName(tag.getName());
        return tagResponse;
    }

    public static List<TagResponse> toResponseList(List<Tag> tags) {
        List<TagResponse> tagResponses = new ArrayList<>();
        for (Tag tag: tags) {
            tagResponses.add(toResponse(tag));
        }
        return tagResponses;
    }

}
