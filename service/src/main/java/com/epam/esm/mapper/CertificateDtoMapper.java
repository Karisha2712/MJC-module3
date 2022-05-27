package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CertificateDtoMapper implements DtoMapper<Certificate, CertificateDto> {
    private final DtoMapper<Tag, TagDto> tagDtoMapper;

    public CertificateDto mapToDto(Certificate certificate) {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(certificate.getId());
        certificateDto.setTitle(certificate.getTitle());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setCreatedDate(DateTimeFormatter.ISO_DATE_TIME
                .format(certificate.getCreatedDate()));
        certificateDto.setLastUpdateDate(DateTimeFormatter.ISO_DATE_TIME
                .format(certificate.getLastUpdateDate()));
        certificateDto.setTags(certificate.getTags() == null
                ? null : certificate.getTags().stream().map(tagDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        return certificateDto;
    }

    public Certificate mapToEntity(CertificateDto certificateDto) {
        Certificate certificate = new Certificate();
        certificate.setTitle(certificateDto.getTitle());
        certificate.setDuration(certificateDto.getDuration());
        certificate.setPrice(certificateDto.getPrice());
        certificate.setDescription(certificateDto.getDescription());
        LocalDateTime createdDate = LocalDateTime.now();
        certificate.setCreatedDate(createdDate);
        certificate.setLastUpdateDate(createdDate);
        return certificate;
    }

}
