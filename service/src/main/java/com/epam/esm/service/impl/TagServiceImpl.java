package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagDtoMapperImpl tagDtoMapper;

    @Override
    public List<TagDto> retrieveAllTags() {
        return tagDao.receiveAllTags().stream().map(tagDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveSingleTag(long id) {
        return tagDtoMapper.mapToDto(tagDao.receiveEntityById(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }

    @Override
    public List<TagDto> retrieveCertificateTags(long id) {
        return tagDao.receiveCertificateTags(id).stream().map(tagDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public long createTag(TagDto tagDto) {
        return (tagDao.receiveTagByName(tagDto.getName()).isPresent()) ?
                tagDao.receiveTagByName(tagDto.getName()).get().getId() :
                tagDao.insertEntity(tagDtoMapper.mapToEntity(tagDto));
    }

    @Override
    public void removeTag(long id) {
        if (tagDao.receiveEntityById(id).isPresent()) {
            tagDao.deleteEntity(id);
        } else {
            throw new TagNotFoundException(id);
        }
    }

}
