package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Page<OrderDto> receivePageOfOrders(
            @RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "2") int elementsPerPageNumber) {
        return orderService.retrievePageOfOrders(currentPage, elementsPerPageNumber);
    }

    @GetMapping("/{id}")
    public OrderDto receiveSingleOrder(@PathVariable Long id) {
        return orderService.retrieveSingleOrder(id);
    }
}
