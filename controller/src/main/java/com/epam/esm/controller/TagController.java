package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;

    @GetMapping("/{id}")
    public TagDto receiveSingleTag(@PathVariable Long id) {
        return tagService.retrieveSingleTag(id);
    }
}
