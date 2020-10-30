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
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Class provides business logic methods for CRD and search operations
 * concerning {@link Tag} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagDtoConverter tagDtoConverter;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagDtoConverter tagDtoConverter) {
        this.tagRepository = tagRepository;
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * Method: find one {@link Tag} entity
     * @param id: tag id
     * @throws ServiceException if entity not found
     * @return instance of ({@link TagDto}
     */
    @Override
    public TagDto findById(Long id) throws ServiceException {
        try {
            Tag tag = tagRepository.findEntityById(id);
            return tagDtoConverter.toResponseDto(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Method: find one or more {@link Tag} entity
     * @return list of ({@link TagDto} entities
     */
    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tagDtoConverter.toResponseDtoList(tags);
    }

    /**
     * Method: save one {@link Tag} entity
     * @throws ServiceException if entity already exists
     * @param tagDto: instance of {@link TagDto}
     */
    @Override
    public void save(TagDto tagDto) throws ServiceException {
        final Tag tag = tagDtoConverter.toNewTag(tagDto);
        Tag savedTag;
        try {
            savedTag = tagRepository.save(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        log.info("Tag has been saved {}", savedTag);
    }

    /**
     * Method: delete one {@link Tag} entity
     * @throws ServiceException if entity not found
     * @param id: tag id
     */
    @Override
    public void delete(Long id) throws ServiceException {
        try {
            tagRepository.delete(id);
            log.info("Tag has been deleted {}", id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
