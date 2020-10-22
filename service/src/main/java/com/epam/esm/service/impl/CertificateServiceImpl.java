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
import static com.epam.esm.exception.message.ServiceExceptionMessage.*;

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

    @Override
    public CertificateDto findById(long id) throws ServiceException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            return certificateDtoConverter.toResponseDto(certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    @Override
    public List<CertificateDto> findAll() {
        final List<Certificate> certificates = certificateRepository.findAll();
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

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

    private void saveTag(long id, CertificateDto certificateDto) {
        List<Long> tagIds = certificateDto.getTagIds();
        tagIds.forEach(tagId -> certificateRepository.saveTag(id, tagId));
    }

    @Override
    public void update(CertificateDto certificateDto) throws ServiceException {
        final Certificate certificate = certificateDtoConverter.toUpdatedCertificate(certificateDto);
        try {
            Certificate updatedCertificate = certificateRepository.update(certificate);
            log.info("Certificate has been updated {}", updatedCertificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            log.info("Certificate has been deleted {}", certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

}
