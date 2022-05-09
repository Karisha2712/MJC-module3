package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT tag FROM Tag tag WHERE tag.name=:tagName";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Tag> getEntityClass() {
        return Tag.class;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(SELECT_TAG_BY_NAME_QUERY, Tag.class);
        query.setParameter("tagName", name);
        return query.getResultStream().findFirst();
    }
}
