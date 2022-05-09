package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

public interface OrderService {
    OrderDto retrieveSingleOrder(Long id);
}
