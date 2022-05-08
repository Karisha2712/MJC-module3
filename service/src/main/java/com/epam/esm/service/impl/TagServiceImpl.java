package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final TagDtoMapperImpl tagDtoMapper;

    public TagDto retrieveSingleTag(Long id) {
        return tagRepository.findById(id)
                .map(tagDtoMapper::mapToDto)
                .orElseThrow(() -> new TagNotFoundException(id));
    }
}
