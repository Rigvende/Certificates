package com.epam.esm.controller;

import com.epam.esm.dto.certificate.CertificateResponse;
import com.epam.esm.dto.certificate.CertificateSaveRequest;
import com.epam.esm.dto.certificate.CertificateUpdateRequest;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<List<CertificateResponse>> findAll() {
        return ResponseEntity.ok(certificateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponse> findById(@PathVariable long id) throws EntityNotFoundException {
        return ResponseEntity.ok(certificateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CertificateSaveRequest certificateSaveRequest)
            throws AlreadyExistException, EntityNotFoundException {
        certificateService.save(certificateSaveRequest);
        return ResponseEntity.ok("Certificate has been saved");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CertificateUpdateRequest certificateUpdateRequest)
            throws EntityNotFoundException, AlreadyExistException {
        certificateService.update(certificateUpdateRequest);
        return ResponseEntity.ok("Certificate has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException {
        certificateService.delete(id);
        return ResponseEntity.ok("Certificate has been deleted");
    }

}
