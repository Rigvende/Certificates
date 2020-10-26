package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Class-converter(mapper) from {@link Tag}
 * to {@link TagDto} and vice versa
 * @author Marianna Patrusova
 * @version 1.0
 */
@Component
public class TagDtoConverter {

    /**
     * Method: convert tag to its DTO
     * @param tag: instance of {@link Tag}
     * @return instance of {@link TagDto}
     */
    public TagDto toResponseDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        tagDto.setStatus(TagDto.TagStatus.PRESENT);
        return tagDto;
    }

    /**
     * Method: convert list of tags to its DTO list
     * @param tags: list of instances of {@link Tag}
     * @return list of instances of {@link TagDto}
     */
    public List<TagDto> toResponseDtoList(List<Tag> tags) {
        List<TagDto> tagDtos = new ArrayList<>();
        if (tags != null) {
            for (Tag tag : tags) {
                tagDtos.add(toResponseDto(tag));
            }
        }
        return tagDtos;
    }

    /**
     * Method: convert tag DTO to new tag
     * @param tagDto: instance of {@link TagDto}
     * @return instance of {@link Tag}
     */
    public Tag toNewTag(TagDto tagDto) {
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

}
