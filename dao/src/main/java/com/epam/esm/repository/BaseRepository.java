package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity>
        extends JpaRepository<T, Long> {
}
