package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {

    EntityManager getEntityManager();

    Class<T> getEntityClass();

    default Optional<T> findById(Long id) {
        return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    }

    @Transactional
    default void saveEntity(T entity) {
        getEntityManager().merge(entity);
    }

    @Transactional
    default void removeEntity(T entity) {
        getEntityManager().remove(entity);
    }
}
