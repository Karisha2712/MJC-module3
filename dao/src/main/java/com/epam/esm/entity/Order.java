package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToMany
    @JoinTable(name = "orders_has_certificates",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "certificates_id")
    )
    private List<Certificate> certificates = new ArrayList<>();

    @Column
    private BigDecimal cost;
}
