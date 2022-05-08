package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }
}
