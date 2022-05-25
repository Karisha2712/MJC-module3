package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.hateoas.CertificatesLinksCreator;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;
    private final CertificatesLinksCreator certificatesLinksCreator;

    @GetMapping("/{id}")
    public CertificateDto receiveSingleCertificate(@PathVariable Long id) {
        CertificateDto certificate = certificateService.retrieveSingleCertificate(id);
        certificatesLinksCreator.createLinks(certificate);
        return certificate;
    }

    @GetMapping
    public Page<CertificateDto> receivePageOfCertificates(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber,
            @RequestParam(name = "textPart", required = false, defaultValue = "") String textPart,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String orderText,
            @RequestParam(name = "tagNames", required = false) Set<String> tagNames) {
        try {
            CertificatesFilter.Order order = orderText != null ? CertificatesFilter.Order.valueOf(orderText)
                    : CertificatesFilter.Order.ASC;
            CertificatesFilter filter = new CertificatesFilter(sortBy, order, textPart, tagNames);
            Page<CertificateDto> certificatePage =
                    certificateService.retrievePageOfCertificatesFoundWithFilter(filter, currentPage, elementsPerPageNumber);
            certificatePage.getPageContent().forEach(certificatesLinksCreator::createLinks);
            certificatesLinksCreator.setFilter(filter);
            certificatesLinksCreator.createPaginationLinks(certificatePage);
            return certificatePage;
        } catch (IllegalArgumentException e) {
            throw new InvalidAttributeValueException();
        }
    }

    @PostMapping
    public ControllerResponse createCertificate(@RequestBody CertificateDto certificateDto) {
        certificateService.saveCertificate(certificateDto);
        return new ControllerResponse("Certificate was created successfully");
    }

    @PatchMapping("/{id}")
    public ControllerResponse updateCertificate(@PathVariable Long id,
                                                @RequestBody CertificateDto certificateDto) {
        certificateService.editCertificate(id, certificateDto);
        return new ControllerResponse("Certificate was updated successfully");
    }

    @DeleteMapping("/{id}")
    public ControllerResponse deleteCertificate(@PathVariable Long id) {
        certificateService.removeCertificate(id);
        return new ControllerResponse("Certificate was deleted successfully");
    }
}
