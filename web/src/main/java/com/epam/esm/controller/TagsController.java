package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.error.CustomError;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class-controller for REST-operations with {@link Tag} entity
 * Provides URL for CRD and search operations
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/tags", produces = "application/json")
public class TagsController {

    private final TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id:[1-9]\\d{0,18}}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            TagDto tagDto = tagService.findById(id);
            return ResponseEntity.ok(tagDto);
        } catch (ServiceException e) {
            log.error(NOT_FOUND + e);
            CustomError error = new CustomError(40401, ERROR_404_TAG, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody TagDto tagDto) throws ServiceException {
        try {
            List<ErrorField> errors = tagService.save(tagDto);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Tag has been saved");
            } else {
                CustomError error = new CustomError(40001, ERROR_400_TAG, errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (ServiceException e) {
            log.error(ALREADY_EXISTS + e);
            CustomError error = new CustomError(50001, ERROR_500_TAG, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id:[1-9]\\d{0,18}}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws ServiceException {
        try {
            tagService.delete(id);
            return ResponseEntity.ok("Tag has been deleted");
        } catch (ServiceException e) {
            log.error(NOT_FOUND + e);
            CustomError error = new CustomError(40401, ERROR_404_TAG, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
