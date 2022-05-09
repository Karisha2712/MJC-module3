package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderDtoMapper implements DtoMapper<Order, OrderDto> {
    private final DtoMapper<Certificate, CertificateDto> certificateDtoMapper;

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setPurchaseDate(DateTimeFormatter.ISO_DATE_TIME.format(order.getPurchaseDate()));
        orderDto.setCertificates(order.getCertificates().stream().map(certificateDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCost(orderDto.getCost());
        LocalDateTime purchaseDate = LocalDateTime.now();
        order.setPurchaseDate(purchaseDate);
        order.setCertificates(orderDto.getCertificates().stream().map(certificateDtoMapper::mapToEntity)
                .collect(Collectors.toList()));
        return null;
    }
}
