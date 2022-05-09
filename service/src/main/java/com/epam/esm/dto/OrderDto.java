package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends AbstractDto {
    private String purchaseDate;
    private BigDecimal cost;
    private List<CertificateDto> certificates = new ArrayList<>();
}
