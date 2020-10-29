package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.error.CustomError;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class-controller for REST-operations with {@link Certificate} entity
 * Provides URL for CRUD and search operations
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/certificates", produces = "application/json")
public class CertificatesController {

    private final CertificateService certificateService;

    @Autowired
    public CertificatesController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public ResponseEntity<List<CertificateDto>> findAll(@RequestParam(required = false) String tagName,
                                                        @RequestParam(required = false) String direction,
                                                        @RequestParam(required = false) String column,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String description) {
        if (tagName != null) {
            return ResponseEntity.ok(certificateService.findAllByTag(tagName));
        } else if (name != null) {
            return ResponseEntity.ok(certificateService.findAllByName(name));
        } else if (description != null) {
            return ResponseEntity.ok(certificateService.findAllByDescription(description));
        } else if (direction != null && column != null) {
            return ResponseEntity.ok(certificateService.findAllSorted(direction, column));
        } else {
            return ResponseEntity.ok(certificateService.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CertificateDto certificateDto = certificateService.findById(id);
            return ResponseEntity.ok(certificateDto);
        } catch (ServiceException e) {
            log.error(NOT_FOUND + ": certificate " + id);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody @Valid CertificateDto certificateDto) {
        try {
            certificateService.save(certificateDto);
            return ResponseEntity.ok("Certificate has been saved");
        } catch (ServiceException e) {
            log.error(ALREADY_EXISTS + ": certificate " + certificateDto.getName());
            CustomError error = new CustomError(50002, ERROR_500_CERTIFICATE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                         @RequestBody @Valid CertificateDto certificateDto) {
        try {
            certificateService.update(id, certificateDto);
            return ResponseEntity.ok("Certificate has been updated");
        } catch (ServiceException e) {
            log.error(NOT_FOUND + ": certificate " + id);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            certificateService.delete(id);
            return ResponseEntity.ok("Certificate has been deleted");
        } catch (ServiceException e) {
            log.error(NOT_FOUND + ": tag " + id);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
