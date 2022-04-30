package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.Optional;

public interface AbstractDao<T extends AbstractEntity> {
    Optional<T> receiveEntityById(long id);

    long insertEntity(T entity);

    void deleteEntity(long id);
}
