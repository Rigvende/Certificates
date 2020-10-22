package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
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
    public ResponseEntity<List<TagDto>> findAll() {
            return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) throws ServiceException {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody TagDto tagDto) throws ServiceException {
        tagService.save(tagDto);
        return ResponseEntity.ok("Tag has been saved");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ServiceException {
        tagService.delete(id);
        return ResponseEntity.ok("Tag has been deleted");
    }

}
