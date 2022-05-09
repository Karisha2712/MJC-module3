package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final DtoMapper<Tag, TagDto> tagDtoMapper;

    public TagDto retrieveSingleTag(Long id) {
        return tagRepository.findById(id)
                .map(tagDtoMapper::mapToDto)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    @Override
    public void removeTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
        tagRepository.removeEntity(tag);
    }

    @Override
    public void saveTag(TagDto tagDto) {
        tagRepository.saveEntity(tagDtoMapper.mapToEntity(tagDto));
    }
}
