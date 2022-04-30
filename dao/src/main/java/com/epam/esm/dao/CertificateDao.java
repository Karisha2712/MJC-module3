package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDao extends AbstractDao<Certificate> {

    List<Certificate> receiveCertificatesByCriteria(String tagName, String textPart, String sortBy, String order);

    void updateCertificate(long id, Certificate certificate);

}
