package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.mapper.CertificateDtoMapper;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final CertificateDtoMapper certificateDtoMapper;
    private final TagDtoMapper tagDtoMapper;

    @Override
    public CertificateDto retrieveSingleCertificate(long id) {
        return certificateRepository.findById(id).filter(certificate -> !certificate.isDeleted())
                .map(certificateDtoMapper::mapToDto)
                .orElseThrow(() -> new CertificateNotFoundException(id));
    }

    @Override
    public Page<CertificateDto> retrievePageOfCertificatesFoundWithFilter(CertificatesFilter filter,
                                                                          int currentPage,
                                                                          int elementsPerPageNumber) {
        if (currentPage <= 0 || elementsPerPageNumber <= 0 || filter.isFilterParamsNotValid()) {
            throw new InvalidAttributeValueException();
        }
        int totalPageNumber = (int) (certificateRepository.count(filter) / elementsPerPageNumber)
                + (certificateRepository.count(filter) % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<CertificateDto> certificateDtos =
                certificateRepository.findAll(filter, PageRequest.of(currentPage - 1, elementsPerPageNumber))
                        .stream()
                        .map(certificateDtoMapper::mapToDto)
                        .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, certificateDtos);
    }

    @Override
    public long saveCertificate(CertificateDto certificateDto) {
        Certificate certificate = certificateDtoMapper.mapToEntity(certificateDto);
        if (certificateDto.getTags() != null) {
            certificate.setTags(retrieveCertificateTags(certificateDto.getTags()));
        }
        certificate.setCreatedDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.save(certificate).getId();
    }

    @Override
    public void removeCertificate(long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        if (certificate.isDeleted()) {
            throw new CertificateNotFoundException(id);
        } else {
            certificate.setDeleted(true);
        }
        certificateRepository.save(certificate);
    }

    @Override
    @Transactional
    public void editCertificate(long id, CertificateDto certificateDto) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        if (certificate.isDeleted()) {
            throw new CertificateNotFoundException(id);
        }
        setCertificateToUpdate(certificateDto, certificate);
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateRepository.save(certificate);
    }

    private List<Tag> retrieveCertificateTags(List<TagDto> tagDtos) {
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            Tag tag;
            if (tagDto.getName() != null) {
                tag = tagRepository.findByName(tagDto.getName()).orElse(tagDtoMapper.mapToDto(tagDto));
                if (isTagInvalid(tagDto, tag)) {
                    throw new InvalidAttributeValueException();
                }
            } else if (tagDto.getId() != null) {
                tag = tagRepository.findById(tagDto.getId())
                        .orElseThrow(() -> new TagNotFoundException(tagDto.getId()));
            } else {
                throw new InvalidAttributeValueException();
            }
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }
        return tags;
    }

    private boolean isTagInvalid(TagDto tagDto, Tag tag) {
        return (tagDto.getId() != null && tag.getId() == null)
                || tagDto.getId() != null && tag.getId() != null
                && !tag.getId().equals(tagDto.getId());
    }

    private void setCertificateToUpdate(CertificateDto certificateDto, Certificate certificate) {
        Optional.ofNullable(certificateDto.getTitle()).ifPresent(certificate::setTitle);
        Optional.ofNullable(certificateDto.getDescription()).ifPresent(certificate::setDescription);
        Optional.ofNullable(certificateDto.getDuration()).ifPresent(certificate::setDuration);
        Optional.ofNullable(certificateDto.getPrice()).ifPresent(certificate::setPrice);
        Optional.ofNullable(certificateDto.getTags())
                .ifPresent(tagDtos -> certificate.setTags(retrieveCertificateTags(tagDtos)));
    }

}
