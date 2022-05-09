package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends AbstractDto {
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
    private UserDto user;
    private List<CertificateDto> certificates = new ArrayList<>();
}
