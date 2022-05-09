package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CertificateDtoMapperImpl implements DtoMapper<Certificate, CertificateDto> {
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(PATTERN);
    private final TagDtoMapperImpl tagDtoMapper;

    public CertificateDto mapToDto(Certificate certificate) {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(certificate.getId());
        certificateDto.setTitle(certificate.getTitle());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setCreatedDate(dateFormat.format(certificate.getCreatedDate()));
        certificateDto.setLastUpdateDate(dateFormat.format(certificate.getLastUpdateDate()));
        certificateDto.setTags(certificate.getTags().stream().map(tagDtoMapper::mapToDto).collect(Collectors.toList()));
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
