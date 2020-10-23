package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Class-controller for REST-operations with {@link Tag} entity
 * Provides endpoints for CRD and search operations
 * @author Marianna Patrusova
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/v1/tags", produces = "application/json")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> findAll() {
            return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping (consumes = "application/json")
    public ResponseEntity<String> save(@RequestBody TagDto tagDto) throws ServiceException {
        tagService.save(tagDto);
        return ResponseEntity.ok("Tag has been saved");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws ServiceException {
        tagService.delete(id);
        return ResponseEntity.ok("Tag has been deleted");
    }

}
