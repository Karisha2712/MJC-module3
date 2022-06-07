package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {

    EntityManager getEntityManager();

    Class<T> getEntityClass();

    default Optional<T> findById(long id) {
        return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    }

    @Transactional
    default long saveEntity(T entity) {
        T newEntity = getEntityManager().merge(entity);
        return newEntity.getId();
    }

    @Transactional
    default void removeEntity(T entity) {
        getEntityManager().remove(entity);
    }

    @Transactional
    default List<T> findAll(int page, int elementsPerPage) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());
        criteria.select(root);
        return getEntityManager().createQuery(criteria)
                .setFirstResult((page - 1) * elementsPerPage)
                .setMaxResults(elementsPerPage)
                .getResultList();
    }

    @Transactional
    default Long countAllElements() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteria.from(getEntityClass());
        criteria.select(criteriaBuilder.count(root));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

}
