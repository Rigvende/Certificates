package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
import java.util.List;

public interface TagService {

    TagDto findById(long id) throws ServiceException;

    List<TagDto> findAll();

    void save(TagDto tagDto) throws ServiceException;

    void delete(long id) throws ServiceException;

}
