package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TagDtoConverterTest {

    private static TagDtoConverter tagDtoConverter;

    @BeforeAll
    static void beforeAll() {
        tagDtoConverter = new TagDtoConverter();
    }

    @Test
    void toResponseDto() {
        Tag tag = new Tag(1L, "spa");
        TagDto dto = tagDtoConverter.toResponseDto(tag);
        assertEquals(dto.getStatus(), TagDto.TagStatus.PRESENT);
    }

    @Test
    void toResponseDtoList() {
        List<Tag> list = Arrays.asList(new Tag(1L, "spa"),
                new Tag(2L, "rest"));
        List<TagDto> dtos = tagDtoConverter.toResponseDtoList(list);
        assertTrue(dtos.size() > 0);
        assertEquals(dtos.get(1).getName(), "rest");
    }

    @Test
    void toNewTag() {
        TagDto dto = new TagDto(null, "summer", TagDto.TagStatus.NEW);
        Tag tag = tagDtoConverter.toNewTag(dto);
        assertEquals(tag.getName(), "summer");
    }

}
