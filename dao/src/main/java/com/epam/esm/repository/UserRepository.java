package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {
    List<Order> findUserOrders(long id, int page, int elementsPerPage);

    Long countUserOrders(long id);
}
