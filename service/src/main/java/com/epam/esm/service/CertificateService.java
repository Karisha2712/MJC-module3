package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;

public interface CertificateService {
    CertificateDto retrieveSingleCertificate(Long id);
    void saveCertificate(CertificateDto certificateDto);
}
