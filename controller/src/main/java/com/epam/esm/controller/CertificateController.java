package com.epam.esm.controller;


import com.epam.esm.service.CertificateService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping
    public List<CertificateDto> getCertificates(@RequestParam(name = "tagName", required = false) String tagName,
                                                @RequestParam(name = "textPart", required = false) String textPart,
                                                @RequestParam(name = "sortBy", required = false) String sortBy,
                                                @RequestParam(name = "order", required = false) String order) {
        return certificateService.retrieveCertificatesByCriteria(tagName, textPart, sortBy, order);
    }

    @GetMapping("/{id}")
    public CertificateDto getCertificate(@PathVariable("id") long id) {
        return certificateService.retrieveSingleCertificate(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable("id") long id) {
        certificateService.removeCertificate(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ControllerResponse createCertificate(@RequestBody CertificateDto certificateDto) {
        long id = certificateService.createCertificate(certificateDto);
        return new ControllerResponse("Certificate was created successfully with id " + id);
    }

    @PatchMapping("/{id}")
    public ControllerResponse updateCertificate(@PathVariable("id") long id, @RequestBody CertificateDto certificateDto) {
        certificateService.updateCertificate(id, certificateDto);
        return new ControllerResponse(String.format("Certificate with id %d was updated successfully", id));
    }
}
