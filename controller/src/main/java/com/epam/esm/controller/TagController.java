package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.TagsLinksCreator;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagsLinksCreator tagsLinksCreator;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TagDto receiveSingleTag(@PathVariable Long id) {
        TagDto tag = tagService.retrieveSingleTag(id);
        tagsLinksCreator.createLinks(tag);
        return tag;
    }

    @GetMapping("/most-widely-used")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TagDto receiveMostWidelyUsedTag() {
        TagDto tag = tagService.retrieveMostWidelyUsedTag();
        tagsLinksCreator.createLinks(tag);
        return tag;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ROLE_USER')")
    public Page<TagDto> receivePageOfTags(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "10") int elementsPerPageNumber) {
        Page<TagDto> tagPage = tagService.retrievePageOfTags(currentPage, elementsPerPageNumber);
        tagPage.getPageContent().forEach(tagsLinksCreator::createLinks);
        tagsLinksCreator.createPaginationLinks(tagPage);
        return tagPage;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ControllerResponse createTag(@Valid @RequestBody TagDto tagDto) {
        long id = tagService.saveTag(tagDto);
        return new ControllerResponse("Tag was created successfully with id " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ControllerResponse deleteTag(@PathVariable Long id) {
        tagService.removeTag(id);
        return new ControllerResponse("Tag was deleted successfully");
    }
}
