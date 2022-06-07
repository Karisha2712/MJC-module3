package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    String DATE_FORMAT = "yyyy-mm-dd'T'hh:mm:ss'Z'";

    @Mapping(target = "purchaseDate", dateFormat = DATE_FORMAT)
    @Mapping(target = "certificates", defaultExpression = "java(com.epam.esm.service.mapper.CertificateDtoMapper.mapToDto())")
    OrderDto mapToDto(Order order);
}
