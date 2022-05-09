package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/{id}")
    public CertificateDto receiveSingleCertificate(@PathVariable Long id) {
        return certificateService.retrieveSingleCertificate(id);
    }

    @GetMapping
    public Page<CertificateDto> receivePageOfCertificates(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return certificateService.retrievePageOfCertificates(currentPage, elementsPerPageNumber);
    }


    @PostMapping
    public ControllerResponse createCertificate(@RequestBody CertificateDto certificateDto) {
        certificateService.saveCertificate(certificateDto);
        return new ControllerResponse("Certificate was created successfully");
    }

    @DeleteMapping("/{id}")
    public ControllerResponse deleteCertificate(@PathVariable Long id) {
        certificateService.removeCertificate(id);
        return new ControllerResponse("Certificate was deleted successfully");
    }
}
