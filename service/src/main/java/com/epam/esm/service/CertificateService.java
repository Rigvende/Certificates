package com.epam.esm.service;

import com.epam.esm.dto.certificate.CertificateResponse;
import com.epam.esm.dto.certificate.CertificateSaveRequest;
import com.epam.esm.dto.certificate.CertificateUpdateRequest;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import java.util.List;

public interface CertificateService {

    CertificateResponse findById(long id) throws EntityNotFoundException;

    List<CertificateResponse> findAll();

    void save(CertificateSaveRequest certificateSaveRequest) throws AlreadyExistException;

    void update(CertificateUpdateRequest certificateUpdateRequest) throws EntityNotFoundException;

    void delete(long id) throws EntityNotFoundException;

}
