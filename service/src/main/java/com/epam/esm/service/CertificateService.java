package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.util.ErrorField;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Interface for business logic concerning {@link Certificate} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
public interface CertificateService {

    List<ErrorField> save(CertificateDto certificateDto) throws ServiceException;

    List<ErrorField> update(Long id, CertificateDto certificateDto) throws ServiceException;

    void delete(Long id) throws ServiceException;

    CertificateDto findById(Long id) throws ServiceException;

    List<CertificateDto> findAll();

    List<CertificateDto> findAllByTag(String tagName);

    List<CertificateDto> findAllSorted(String direction, String column);

    List<CertificateDto> findAllByName(String name);

    List<CertificateDto> findAllByDescription(String description);

}
