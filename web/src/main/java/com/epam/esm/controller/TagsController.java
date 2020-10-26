package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.error.CustomError;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class-controller for REST-operations with {@link Tag} entity
 * Provides endpoints for CRD and search operations
 * @author Marianna Patrusova
 * @version 1.0
 */
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            TagDto tagDto = tagService.findById(id);
            return ResponseEntity.ok(tagDto);
        } catch (ServiceException e) {
            CustomError error = new CustomError(40401, ERROR_404_TAG);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody TagDto tagDto) throws ServiceException {
        try {
            tagService.save(tagDto);
            return ResponseEntity.ok("Tag has been saved");
        } catch (ServiceException e) {
            CustomError error = new CustomError(50001, ERROR_500_TAG);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws ServiceException {
        try {
            tagService.delete(id);
            return ResponseEntity.ok("Tag has been deleted");
        } catch (ServiceException e) {
            CustomError error = new CustomError(40401, ERROR_404_TAG);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}
