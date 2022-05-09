package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateDtoMapperImpl;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;

    private final DtoMapper<Certificate, CertificateDto> certificateDtoMapper;

    public CertificateDto retrieveSingleCertificate(Long id) {
        return certificateRepository.findById(id).map(certificateDtoMapper::mapToDto)
                .orElseThrow(() -> new CertificateNotFoundException(id));
    }

}
