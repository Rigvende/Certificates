package com.epam.esm.service.impl;

import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.epam.esm.exception.message.ServiceExceptionMessage.*;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDtoConverter tagDtoConverter;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagDtoConverter tagDtoConverter) {
        this.tagRepository = tagRepository;
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public TagDto findById(long id) throws ServiceException {
        try {
            Tag tag = tagRepository.findEntityById(id);
            return tagDtoConverter.toResponseDto(tag);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tagDtoConverter.toResponseDtoList(tags);
    }

    @Override
    public void save(TagDto tagDto) throws ServiceException {
        final Tag tag = tagDtoConverter.toNewTag(tagDto);
        Tag savedTag;
        try {
            savedTag = tagRepository.save(tag);
        } catch (DaoException e) {
            throw new ServiceException(ALREADY_EXISTS, e);
        }
        log.info("Tag has been saved {}", savedTag);
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            Tag tag = tagRepository.findEntityById(id);
            log.info("Tag has been deleted {}", tag);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

}
