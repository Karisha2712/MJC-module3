package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.pagination.Page;

public interface TagService {
    TagDto retrieveSingleTag(long id);

    void removeTag(long id);

    void saveTag(TagDto tagDto);

    Page<TagDto> retrievePageOfTags(int currentPage, int elementsPerPageNumber);
}
