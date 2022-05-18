package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String DESCRIPTION = "description";
    private static final String TAGS = "tags";
    private static final String LAST_UPDATED_DATE = "lastUpdateDate";

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
    public void updateCertificate(Certificate certificate) {
        entityManager.merge(certificate);
    }

}
