package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TagDtoConverter {

    public TagDto toResponseDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    public List<TagDto> toResponseDtoList(List<Tag> tags) {
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag: tags) {
            tagDtos.add(toResponseDto(tag));
        }
        return tagDtos;
    }

    public Tag toNewTag(TagDto tagDto) {
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

}
