package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;

public interface CertificateRepository extends BaseRepository<Certificate> {
    void updateCertificate(Certificate certificate);
}
