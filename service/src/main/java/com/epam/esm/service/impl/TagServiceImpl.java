package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.TagAlreadyExistsException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final TagDtoMapper tagDtoMapper;

    @Override
    public TagDto retrieveSingleTag(long id) {
        return tagRepository.findById(id)
                .map(tagDtoMapper::mapToEntity)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    @Override
    public void removeTag(long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
        tagRepository.removeEntity(tag);
    }

    @Override
    public long saveTag(TagDto tagDto) {
        Optional<Tag> optionalTag = tagRepository.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            throw new TagAlreadyExistsException(tagDto.getName());
        } else {
            return tagRepository.saveEntity(tagDtoMapper.mapToDto(tagDto));
        }
    }

    @Override
    public Page<TagDto> retrievePageOfTags(int currentPage, int elementsPerPageNumber) {
        if (currentPage <= 0 || elementsPerPageNumber <= 0) {
            throw new InvalidAttributeValueException();
        }
        int totalPageNumber = (int) ((tagRepository.countAllElements() / elementsPerPageNumber)
                + (tagRepository.countAllElements() % elementsPerPageNumber > 0 ? 1 : 0));
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<TagDto> tagDtos = tagRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(tagDtoMapper::mapToEntity)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, tagDtos);
    }

    @Override
    public TagDto retrieveMostWidelyUsedTag() {
        return tagRepository.findMostWidelyUsedTag().map(tagDtoMapper::mapToEntity)
                .orElseThrow(() -> new TagNotFoundException(0));
    }
}
