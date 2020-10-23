package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDtoConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.epam.esm.exception.message.ServiceExceptionMessage.ALREADY_EXISTS;
import static com.epam.esm.exception.message.ServiceExceptionMessage.NOT_FOUND;

/**
 * Class provides business logic methods for CRUD and search operations
 * concerning {@link Certificate} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateDtoConverter certificateDtoConverter;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  CertificateDtoConverter certificateDtoConverter) {
        this.certificateRepository = certificateRepository;
        this.certificateDtoConverter = certificateDtoConverter;
    }

    /**
     * Method: find one {@link Certificate} entity
     * @param id: certificate id
     * @throws ServiceException if entity not found
     * @return instance of ({@link CertificateDto}
     */
    @Override
    public CertificateDto findById(long id) throws ServiceException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            return certificateDtoConverter.toResponseDto(certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    /**
     * Method: find one or more {@link Certificate} entity
     * @return list of ({@link CertificateDto} entities
     */
    @Override
    public List<CertificateDto> findAll() {
        final List<Certificate> certificates = certificateRepository.findAll();
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    /**
     * Method: save one {@link Certificate} entity and its tags
     * @throws ServiceException if entity already exists
     * @param certificateDto: instance of {@link CertificateDto}
     */
    @Override
    public void save(CertificateDto certificateDto) throws ServiceException {
        final Certificate certificate = certificateDtoConverter.toNewCertificate(certificateDto);
        Certificate savedCertificate;
        try {
            savedCertificate = certificateRepository.save(certificate);
        } catch (DaoException e) {
            throw new ServiceException(ALREADY_EXISTS, e);
        }
        //add values to tag-certificate cross table:
        long id = savedCertificate.getId();
        saveTag(id, certificateDto);
        log.info("Certificate has been saved {}", savedCertificate);
    }

    /**
     * Method: update fields of one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param certificateDto: instance of {@link CertificateDto}
     */
    @Override
    public void update(CertificateDto certificateDto) throws ServiceException {
        final Certificate certificate = certificateDtoConverter.toUpdatedCertificate(certificateDto);
        try {
            Certificate updatedCertificate = certificateRepository.update(certificate);
            //add values to tag-certificate cross table:
            long id = updatedCertificate.getId();
            saveTag(id, certificateDto);
            log.info("Certificate has been updated {}", updatedCertificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    /**
     * Method: delete one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param id: certificate id
     */
    @Override
    public void delete(long id) throws ServiceException {
        try {
            certificateRepository.delete(id);
            log.info("Certificate has been deleted {}", id);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    private void saveTag(long id, CertificateDto certificateDto) {
        List<Long> tagIds = certificateDto.getTagIds();
        tagIds.forEach(tagId -> certificateRepository.saveCertificateCrossTag(id, tagId));
    }

}
