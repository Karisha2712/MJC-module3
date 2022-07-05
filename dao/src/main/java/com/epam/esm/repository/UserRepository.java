package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByLogin(String login);

    boolean existsUserByLogin(String login);
}
