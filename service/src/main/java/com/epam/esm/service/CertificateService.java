package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Interface for business logic concerning {@link Certificate} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
public interface CertificateService {

    CertificateDto findById(Long id) throws ServiceException;

    List<CertificateDto> findAll();

    void save(CertificateDto certificateDto) throws ServiceException;

    void update(Long id, CertificateDto certificateDto) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<CertificateDto> findAllByTag(String tagName);

}
