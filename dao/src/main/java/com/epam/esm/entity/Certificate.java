package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "certificates")
public class Certificate extends AbstractEntity {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer duration;

    @Column
    private BigDecimal price;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdateDate;
}

