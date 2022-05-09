package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

public interface TagService {
    TagDto retrieveSingleTag(Long id);

    void removeTag(Long id);

    void saveTag(TagDto tagDto);
}
