package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}")
    public TagDto receiveSingleTag(@PathVariable Long id) {
        return tagService.retrieveSingleTag(id);
    }

    @GetMapping("/most-widely-used")
    public TagDto receiveMostWidelyUsedTag() {
        return tagService.retrieveMostWidelyUsedTag();
    }

    @GetMapping
    public Page<TagDto> receivePageOfTags(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return tagService.retrievePageOfTags(currentPage, elementsPerPageNumber);
    }

    @PostMapping
    public ControllerResponse createTag(@RequestBody TagDto tagDto) {
        tagService.saveTag(tagDto);
        return new ControllerResponse("Tag was created successfully");
    }

    @DeleteMapping("/{id}")
    public ControllerResponse deleteTag(@PathVariable Long id) {
        tagService.removeTag(id);
        return new ControllerResponse("Tag was deleted successfully");
    }
}
