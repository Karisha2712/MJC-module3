package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.pagination.Page;

public interface CertificateService {
    CertificateDto retrieveSingleCertificate(long id);

    Page<CertificateDto> retrievePageOfCertificatesFoundWithFilter(CertificatesFilter filter,
                                                                   int currentPage,
                                                                   int elementsPerPageNumber);

    void saveCertificate(CertificateDto certificateDto);

    void removeCertificate(long id);

    void editCertificate(long id, CertificateDto certificateDto);
}
