package com.epam.esm.controller;


import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;


}
