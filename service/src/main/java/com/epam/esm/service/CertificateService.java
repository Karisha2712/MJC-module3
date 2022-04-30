package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public interface CertificateService {
    CertificateDto retrieveSingleCertificate(long id);

    List<CertificateDto> retrieveCertificatesByCriteria(String tagName, String textPart,
                                                        String sortBy, String order);

    long createCertificate(CertificateDto certificateDto);

    void removeCertificate(long id);

    void updateCertificate(long id, CertificateDto certificateDto);

}
