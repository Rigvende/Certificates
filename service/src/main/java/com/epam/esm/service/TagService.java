package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Interface for business logic concerning {@link Tag} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
public interface TagService {

    void save(TagDto tagDto) throws ServiceException;

    void delete(Long id) throws ServiceException;

    TagDto findById(Long id) throws ServiceException;

    List<TagDto> findAll();

}
