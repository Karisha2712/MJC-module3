package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
