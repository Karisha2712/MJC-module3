package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final DtoMapper<Order, OrderDto> orderDtoMapper;

    @Override
    public OrderDto retrieveSingleOrder(Long id) {
        return orderRepository.findById(id).map(orderDtoMapper::mapToDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
