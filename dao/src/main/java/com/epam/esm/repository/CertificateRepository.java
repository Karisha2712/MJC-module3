package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.filter.CertificatesFilter;

import java.util.List;

public interface CertificateRepository extends BaseRepository<Certificate> {

    List<Certificate> findCertificatesWithFilter(CertificatesFilter filter, int page, int elementsPerPage);

    long countFilteredElements(CertificatesFilter filter);
}
