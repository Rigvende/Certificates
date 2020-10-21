package com.epam.esm.service.impl;

import com.epam.esm.dto.certificate.CertificateResponse;
import com.epam.esm.dto.certificate.CertificateSaveRequest;
import com.epam.esm.dto.certificate.CertificateUpdateRequest;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public CertificateResponse findById(long id) throws EntityNotFoundException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            return CertificateResponse.toResponse(certificate);
        } catch (DaoException e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Override
    public List<CertificateResponse> findAll() {
        final List<Certificate> certificates = certificateRepository.findAll();
        return CertificateResponse.toResponseList(certificates);
    }

    @Override
    public void save(CertificateSaveRequest certificateSaveRequest) throws AlreadyExistException {
        final Certificate certificate = certificateSaveRequest.toCertificate();
        Certificate savedCertificate;
        try {
            savedCertificate = certificateRepository.save(certificate);
        } catch (DaoException e) {
            throw new AlreadyExistException(e);
        }
        //add values to tag-certificate cross table:
        long certificateId = savedCertificate.getId();
        List<Long> tagIds = certificateSaveRequest.getTagIds();
        tagIds.forEach(tagId -> certificateRepository.saveTag(certificateId, tagId));
        log.info("Certificate has been saved {}", savedCertificate);
    }

    @Override
    public void update(CertificateUpdateRequest certificateUpdateRequest) throws EntityNotFoundException {
        final Certificate certificate = certificateUpdateRequest.toCertificate();
        try {
            Certificate updatedCertificate = certificateRepository.update(certificate);
            log.info("Certificate has been updated {}", updatedCertificate);
        } catch (DaoException e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            log.info("Certificate has been deleted {}", certificate);
        } catch (DaoException e) {
            throw new EntityNotFoundException(e);
        }
    }

}
