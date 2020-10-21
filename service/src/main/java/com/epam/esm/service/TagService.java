package com.epam.esm.service;

import com.epam.esm.dto.tag.TagResponse;
import com.epam.esm.dto.tag.TagSaveRequest;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import java.util.List;

public interface TagService {

    TagResponse findById(long id) throws EntityNotFoundException;

    List<TagResponse> findAll();

    void save(TagSaveRequest tagSaveRequest) throws AlreadyExistException;

    void delete(long id) throws EntityNotFoundException;

}
