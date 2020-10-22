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
@RequestMapping("/v1/certificates")
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
    public ResponseEntity<CertificateDto> findById(@PathVariable long id) throws ServiceException {
        return ResponseEntity.ok(certificateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CertificateDto certificateDto) throws ServiceException {
        certificateService.save(certificateDto);
        return ResponseEntity.ok("Certificate has been saved");
    }

    @PatchMapping
    public ResponseEntity<String> update(@RequestBody CertificateDto certificateDto) throws ServiceException {
        certificateService.update(certificateDto);
        return ResponseEntity.ok("Certificate has been updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ServiceException {
        certificateService.delete(id);
        return ResponseEntity.ok("Certificate has been deleted");
    }

}
