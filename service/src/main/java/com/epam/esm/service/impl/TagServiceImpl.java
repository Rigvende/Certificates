package com.epam.esm.service.impl;

import com.epam.esm.dto.tag.TagResponse;
import com.epam.esm.dto.tag.TagSaveRequest;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagResponse findById(long id) throws EntityNotFoundException {
        try {
            Tag tag = tagRepository.findEntityById(id);
            return TagResponse.toResponse(tag);
        } catch (DaoException e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Override
    public List<TagResponse> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return TagResponse.toResponseList(tags);
    }

    @Override
    public void save(TagSaveRequest tagSaveRequest) throws AlreadyExistException {
        final Tag tag = tagSaveRequest.toTag();
        Tag savedTag;
        try {
            savedTag = tagRepository.save(tag);
        } catch (DaoException e) {
            throw new AlreadyExistException(e);
        }
        log.info("Tag has been saved {}", savedTag);
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        try {
            Tag tag = tagRepository.findEntityById(id);
            log.info("Tag has been deleted {}", tag);
        } catch (DaoException e) {
            throw new EntityNotFoundException(e);
        }
    }

}
