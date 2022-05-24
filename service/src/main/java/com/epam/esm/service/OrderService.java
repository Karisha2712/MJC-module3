package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.pagination.Page;

public interface OrderService {
    OrderDto retrieveSingleOrder(long id);

    Page<OrderDto> retrievePageOfOrders(int currentPage, int elementsPerPageNumber);

    void saveOrder(long userId, OrderDto orderDto);
}
