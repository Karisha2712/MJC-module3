package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final DtoMapper<Order, OrderDto> orderDtoMapper;

    @Override
    public OrderDto retrieveSingleOrder(long id) {
        return orderRepository.findById(id).map(orderDtoMapper::mapToDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Page<OrderDto> retrievePageOfOrders(int currentPage, int elementsPerPageNumber) {
        int totalPageNumber = (int) (orderRepository.countAllElements() / elementsPerPageNumber)
                + (orderRepository.countAllElements() % elementsPerPageNumber > 0 ? 1 : 0);
        List<OrderDto> certificateDtos = orderRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, certificateDtos);
    }
}
