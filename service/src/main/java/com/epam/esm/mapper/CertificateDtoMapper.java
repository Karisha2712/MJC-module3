package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagDtoMapper.class})
public interface CertificateDtoMapper {
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    Certificate mapToEntity(CertificateDto certificateDto);

    @Mapping(target = "createdDate", dateFormat = DATE_FORMAT)
    @Mapping(target = "lastUpdateDate", dateFormat = DATE_FORMAT)
    CertificateDto mapToDto(Certificate certificate);
}
