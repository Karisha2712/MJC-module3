package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Order extends AbstractEntity {
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
}
