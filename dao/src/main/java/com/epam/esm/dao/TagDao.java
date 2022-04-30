package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends AbstractDao<Tag> {
    List<Tag> receiveAllTags();

    List<Tag> receiveCertificateTags(long id);

    Optional<Tag> receiveTagByName(String name);
}
