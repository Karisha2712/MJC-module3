package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByLogin(String login);

    List<Order> findUserOrders(long id, int page, int elementsPerPage);

    Long countUserOrders(long id);
}
