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
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column
    private BigDecimal cost;
}
