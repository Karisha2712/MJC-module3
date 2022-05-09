package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/{id}")
    public CertificateDto receiveSingleCertificate(@PathVariable Long id) {
        return certificateService.retrieveSingleCertificate(id);
    }
}
