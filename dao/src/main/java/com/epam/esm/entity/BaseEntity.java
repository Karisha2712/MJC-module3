package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
