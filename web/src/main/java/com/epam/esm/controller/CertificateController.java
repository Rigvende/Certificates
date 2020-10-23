package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Class-controller for REST-operations with {@link Certificate} entity
 * Provides endpoints for CRUD and search operations
 * @author Marianna Patrusova
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/v1/certificates", produces = "application/json")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll() {
        return ResponseEntity.ok(certificateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> findById(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok(certificateService.findById(id));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> save(@RequestBody CertificateDto certificateDto) throws ServiceException {
        certificateService.save(certificateDto);
        return ResponseEntity.ok("Certificate has been saved");
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<String> update(@PathVariable("id") Long id,
                                         @RequestBody CertificateDto certificateDto) throws ServiceException {
        certificateService.update(id, certificateDto);
        return ResponseEntity.ok("Certificate has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws ServiceException {
        certificateService.delete(id);
        return ResponseEntity.ok("Certificate has been deleted");
    }

}
