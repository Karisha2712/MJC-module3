package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.pagination.Page;

public interface CertificateService {
    CertificateDto retrieveSingleCertificate(long id);

    Page<CertificateDto> retrievePageOfCertificates(int currentPage, int elementsPerPageNumber);

    void saveCertificate(CertificateDto certificateDto);

    void removeCertificate(long id);
}
