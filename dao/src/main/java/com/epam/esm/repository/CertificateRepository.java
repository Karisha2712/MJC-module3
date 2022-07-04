package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CertificateRepository extends BaseRepository<Certificate> {
    Page<Certificate> findAll(Specification<Certificate> filter, Pageable page);

    long count(Specification<Certificate> filter);
}
