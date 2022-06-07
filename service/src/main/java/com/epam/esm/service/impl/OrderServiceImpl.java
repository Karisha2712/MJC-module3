package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final CertificateRepository certificateRepository;

    private final OrderDtoMapper orderDtoMapper;

    @Override
    public OrderDto retrieveSingleOrder(long id) {
        return orderRepository.findById(id).map(orderDtoMapper::mapToDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Page<OrderDto> retrievePageOfOrders(int currentPage, int elementsPerPageNumber) {
        int totalPageNumber = (int) (orderRepository.countAllElements() / elementsPerPageNumber)
                + (orderRepository.countAllElements() % elementsPerPageNumber > 0 ? 1 : 0);
        if (currentPage > totalPageNumber) {
            throw new PageNotFoundException(currentPage, totalPageNumber);
        }
        List<OrderDto> certificateDtos = orderRepository.findAll(currentPage, elementsPerPageNumber)
                .stream()
                .map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return new Page<>(currentPage, totalPageNumber, elementsPerPageNumber, certificateDtos);
    }

    @Override
    public long saveOrder(long userId, OrderDto orderDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Certificate> certificates = orderDto.getCertificates()
                .stream()
                .map(certificate ->
                        certificateRepository.findById(certificate.getId())
                                .orElseThrow(() -> new CertificateNotFoundException(certificate.getId())))
                .collect(Collectors.toList());
        Order order = new Order();
        order.setPurchaseDate(LocalDateTime.now());
        order.setUser(user);
        order.setCertificates(certificates);
        BigDecimal cost = certificates.stream().map(Certificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setCost(cost);
        return orderRepository.saveEntity(order);
    }

}
