package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> retrieveAllTags();

    List<TagDto> retrieveCertificateTags(long id);

    TagDto retrieveSingleTag(long id);

    long createTag(TagDto tagDto);

    void removeTag(long id);
}
