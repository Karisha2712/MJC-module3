package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoMapper implements DtoMapper<Tag, TagDto> {
    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        tagDto.setId(tag.getId());
        return tagDto;
    }

    public Tag mapToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        return tag;
    }
}
