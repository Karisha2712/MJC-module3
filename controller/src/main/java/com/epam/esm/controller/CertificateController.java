package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDto;
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

    @PostMapping
    public ControllerResponse createCertificate(@RequestBody CertificateDto certificateDto) {
        certificateService.saveCertificate(certificateDto);
        return new ControllerResponse("Certificate was created successfully");
    }
}
