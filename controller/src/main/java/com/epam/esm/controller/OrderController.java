package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.OrdersLinksCreator;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrdersLinksCreator ordersLinksCreator;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Page<OrderDto> receivePageOfOrders(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        Page<OrderDto> orderPage = orderService.retrievePageOfOrders(currentPage, elementsPerPageNumber);
        orderPage.getPageContent().forEach(ordersLinksCreator::createLinks);
        ordersLinksCreator.createPaginationLinks(orderPage);
        return orderPage;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public OrderDto receiveSingleOrder(@PathVariable Long id) {
        OrderDto order = orderService.retrieveSingleOrder(id);
        ordersLinksCreator.createLinks(order);
        return order;
    }
}
