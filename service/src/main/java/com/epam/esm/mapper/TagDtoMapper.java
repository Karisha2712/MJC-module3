package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {
    TagDto mapToEntity(Tag tag);

    Tag mapToDto(TagDto tagDto);
}
