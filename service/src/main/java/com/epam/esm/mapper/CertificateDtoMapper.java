package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CertificateDtoMapper {

    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Mapping(target = "tags", defaultExpression = "java(com.epam.esm.service.mapper.TagDtoMapper.mapToTag())")
    Certificate mapToEntity(CertificateDto certificateDto);

    @Mapping(target = "createdDate", dateFormat = DATE_FORMAT)
    @Mapping(target = "lastUpdateDate", dateFormat = DATE_FORMAT)
    @Mapping(target = "tags", defaultExpression = "java(com.epam.esm.service.mapper.TagDtoMapper.mapToTagDto())")
    CertificateDto mapToDto(Certificate certificate);
}
