package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderRepository extends BaseRepository<Order> {
    Page<Order> findByUserId(Long userId, org.springframework.data.domain.Pageable pageable);

    long countOrdersByUserId(Long userId);
}
