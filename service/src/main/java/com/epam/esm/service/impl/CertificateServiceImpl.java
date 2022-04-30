package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateAndTagDao;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.mapper.CertificateDtoMapperImpl;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final TagDtoMapperImpl tagDtoMapper;
    private final TagDao tagDao;
    private final CertificateDtoMapperImpl certificateDtoMapper;
    private final CertificateAndTagDao certificateAndTagDao;

    @Override
    @Transactional
    public CertificateDto retrieveSingleCertificate(long id) {
        return certificateDtoMapper.mapToDto(certificateDao.receiveEntityById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id)));
    }

    @Override
    @Transactional
    public long createCertificate(CertificateDto certificateDto) {
        Certificate certificate = certificateDtoMapper.mapToEntity(certificateDto);
        long certificateId = certificateDao.insertEntity(certificate);
        certificateDto.getTags()
                .forEach(tag -> {
                    long tagId = tagService.createTag(tag);
                    certificateAndTagDao.insertRelationship(certificateId, tagId);
                });
        return certificateId;
    }

    @Override
    public void removeCertificate(long id) {
        if (certificateDao.receiveEntityById(id).isPresent()) {
            certificateDao.deleteEntity(id);
        } else {
            throw new CertificateNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void updateCertificate(long id, CertificateDto certificateDto) {
        if (certificateDao.receiveEntityById(id).isPresent()) {
            Certificate certificate = certificateDtoMapper.mapToEntity(certificateDto);
            certificateDao.updateCertificate(id, certificate);
            if (certificateDto.getTags() != null) {
                updateTags(id, certificateDto);
            }
        } else {
            throw new CertificateNotFoundException(id);
        }
    }

    @Override
    public List<CertificateDto> retrieveCertificatesByCriteria(String tagName, String textPart,
                                                               String sortBy, String order) {
        if (sortBy != null && !isCriteriaValid(sortBy, order)) {
            throw new InvalidAttributeValueException();
        }
        return certificateDao.receiveCertificatesByCriteria(tagName, textPart, sortBy, order)
                .stream()
                .map(certificateDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private boolean isCriteriaValid(String sortBy, String order) {
        String date = "created_date";
        String title = "title";
        String ascOrder = "ASC";
        String descOrder = "DESC";
        return (sortBy.equalsIgnoreCase(date) || sortBy.equalsIgnoreCase(title))
                && (order == null || order.equalsIgnoreCase(ascOrder) || order.equalsIgnoreCase(descOrder));
    }

    private void updateTags(long id, CertificateDto certificateDto) {
        List<Tag> oldTags = tagDao.receiveCertificateTags(id);
        List<Tag> newTags = certificateDto.getTags().stream().map(tagDtoMapper::mapToEntity).collect(Collectors.toList());
        oldTags.forEach(oldTag -> {
            if (!newTags.contains(oldTag)) {
                certificateAndTagDao.deleteRelationship(oldTag.getId(), id);
            }
        });
        newTags.forEach(newTag -> {
            if (!oldTags.contains(newTag)) {
                long tagId = tagService.createTag(tagDtoMapper.mapToDto(newTag));
                certificateAndTagDao.insertRelationship(id, tagId);
            }
        });
    }

}
