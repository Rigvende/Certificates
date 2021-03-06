package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.error.CustomError;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ErrorField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id:[1-9]\\d{0,18}}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            CertificateDto certificateDto = certificateService.findById(id);
            return ResponseEntity.ok(certificateDto);
        } catch (ServiceException e) {
            log.error(NOT_FOUND + e);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody CertificateDto certificateDto) {
        try {
            List<ErrorField> errors = certificateService.save(certificateDto);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Certificate has been saved");
            } else {
                CustomError error = new CustomError(40002, ERROR_400_CERTIFICATE, errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (ServiceException e) {
            log.error(ALREADY_EXISTS + e);
            CustomError error = new CustomError(50002, ERROR_500_CERTIFICATE, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PatchMapping(path = "/{id:[1-9]\\d{0,18}}", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                         @RequestBody CertificateDto certificateDto) {
        try {
            List<ErrorField> errors = certificateService.update(id, certificateDto);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Certificate has been updated");
            } else {
                CustomError error = new CustomError(40002, ERROR_400_CERTIFICATE, errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (ServiceException e) {
            log.error(NOT_FOUND + e);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id:[1-9]\\d{0,18}}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            certificateService.delete(id);
            return ResponseEntity.ok("Certificate has been deleted");
        } catch (ServiceException e) {
            log.error(NOT_FOUND + e);
            CustomError error = new CustomError(40402, ERROR_404_CERTIFICATE, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
