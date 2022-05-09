package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
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

    @PostMapping
    public ControllerResponse createTag(@RequestBody TagDto tagDto) {
        tagService.saveTag(tagDto);
        return new ControllerResponse("Tag was created successfully");
    }
}
