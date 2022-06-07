package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private static final String TAG_NAME = "name";
    private static final String SELECT_MOST_WIDELY_USED_TAG_QUERY =
            "SELECT tags.id, tags.name from (orders \n" +
                    "JOIN orders_has_certificates ON orders.id = orders_has_certificates.orders_id \n" +
                    "JOIN certificates ON orders_has_certificates.certificates_id = certificates.id \n" +
                    "JOIN certificates_has_tags ON certificates.id = certificates_has_tags.certificates_id\n" +
                    "JOIN tags ON certificates_has_tags.tags_id = tags.id)\n" +
                    "WHERE users_id =\n" +
                    "(SELECT users_id from orders GROUP BY users_id ORDER BY sum(cost) DESC LIMIT 1)\n" +
                    "GROUP BY tags_id \n" +
                    "ORDER BY count(tags_id) DESC\n" +
                    "LIMIT 1";

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
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(TAG_NAME), name));
        criteriaQuery.select(root);
        List<Tag> tags = getEntityManager().createQuery(criteriaQuery).getResultList();
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTag() {
        Query nativeQuery = getEntityManager().createNativeQuery(SELECT_MOST_WIDELY_USED_TAG_QUERY, Tag.class);
        List<Tag> tags = nativeQuery.getResultList();
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }
}
