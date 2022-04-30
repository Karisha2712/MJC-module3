package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Certificate extends AbstractEntity {
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
}

