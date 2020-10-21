package com.epam.esm.controller;

import com.epam.esm.dto.tag.TagResponse;
import com.epam.esm.dto.tag.TagSaveRequest;
import com.epam.esm.exception.AlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> findAll() {
            return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> findById(@PathVariable long id) throws EntityNotFoundException {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody TagSaveRequest tagSaveRequest)
            throws AlreadyExistException, EntityNotFoundException {
        tagService.save(tagSaveRequest);
        return ResponseEntity.ok("Tag has been saved");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException {
        tagService.delete(id);
        return ResponseEntity.ok("Tag has been deleted");
    }

}
