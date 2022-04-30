package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.retrieveAllTags();
    }

    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable("id") long id) {
        return tagService.retrieveSingleTag(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ControllerResponse createTag(@RequestBody TagDto tagDto) {
        long id = tagService.createTag(tagDto);
        return new ControllerResponse("Tag was created successfully with id " + id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") long id) {
        tagService.removeTag(id);
    }

}
