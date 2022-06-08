package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CertificateDtoMapper.class})
public interface OrderDtoMapper {

    String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";

    @Mapping(target = "purchaseDate", dateFormat = DATE_FORMAT)
    OrderDto mapToDto(Order order);
}
