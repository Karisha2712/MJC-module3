package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private static final String TITLE = "title";
    private static final String IS_DELETED = "isDeleted";
    private static final String ID = "id";
    private static final String PERCENT = "%";
    private static final String TAGS = "tags";
    private static final String TAG_NAME = "name";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Certificate> getEntityClass() {
        return Certificate.class;
    }

    @Override
    @Transactional
    public List<Certificate> findCertificatesWithFilter(CertificatesFilter filter,
                                                        int page,
                                                        int elementsPerPage) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(root);
        applyFilters(filter, criteriaBuilder, criteriaQuery, root);
        if (filter.getSortBy() == null) {
            filter.setSortBy(ID);
        }
        if (filter.getOrder() == CertificatesFilter.Order.ASC) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(filter.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(filter.getSortBy())));
        }
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * elementsPerPage)
                .setMaxResults(elementsPerPage)
                .getResultList();
    }

    public long countFilteredElements(CertificatesFilter filter) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        applyFilters(filter, criteriaBuilder, criteriaQuery, root);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private void applyFilters(CertificatesFilter filter, CriteriaBuilder criteriaBuilder,
                              CriteriaQuery criteriaQuery, Root<Certificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getTextPart() != null) {
            predicates.add(criteriaBuilder.like(root.get(TITLE), PERCENT + filter.getTextPart() + PERCENT));
        }
        if (filter.getTagNames() != null) {
            Set<String> tags = filter.getTagNames();
            tags.forEach(name -> {
                ListJoin<Certificate, Tag> tagsJoin = root.joinList(TAGS);
                predicates.add(criteriaBuilder.equal(tagsJoin.get(TAG_NAME), name));
            });
        }
        predicates.add(criteriaBuilder.isFalse(root.get(IS_DELETED)));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    }

}
