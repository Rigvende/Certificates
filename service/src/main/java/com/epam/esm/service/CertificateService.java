package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import java.util.List;

public interface CertificateService {

    CertificateDto findById(long id) throws ServiceException;

    List<CertificateDto> findAll();

    void save(CertificateDto certificateDto) throws ServiceException;

    void update(CertificateDto certificateDto) throws ServiceException;

    void delete(long id) throws ServiceException;

}
