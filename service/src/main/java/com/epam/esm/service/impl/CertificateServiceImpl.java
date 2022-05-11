package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final DtoMapper<Certificate, CertificateDto> certificateDtoMapper;
    private final DtoMapper<Tag, TagDto> tagDtoMapper;

    @Override
    public CertificateDto retrieveSingleCertificate(long id) {
        return certificateRepository.findById(id).map(certificateDtoMapper::mapToDto)
                .orElseThrow(() -> new CertificateNotFoundException(id));
    }

    @Override
    public Page<CertificateDto> retrievePageOfCertificates(int currentPage, int elementsPerPageNumber) {
        int totalPageNumber = (int) (certificateRepository.countAllElements() / elementsPerPageNumber)
                + (certificateRepository.countAllElements() % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<CertificateDto> certificateDtos = certificateRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(certificateDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, certificateDtos);
    }

    @Override
    public void saveCertificate(CertificateDto certificateDto) {
        Certificate certificate = certificateDtoMapper.mapToEntity(certificateDto);
        certificate.setTags(retrieveNotExistingTags(certificateDto.getTags()));
        certificateRepository.saveEntity(certificate);
    }

    @Override
    public void removeCertificate(long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        certificateRepository.removeEntity(certificate);
    }

    private List<Tag> retrieveNotExistingTags(List<TagDto> tagDtos) {
        return tagDtos.stream()
                .map(tag -> tagRepository.findByName(tag.getName()).orElse(tagDtoMapper.mapToEntity(tag)))
                .collect(Collectors.toList());
    }
}
