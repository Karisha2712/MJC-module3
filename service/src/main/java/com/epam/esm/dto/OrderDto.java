package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends AbstractDto {
    private String purchaseDate;
    private BigDecimal cost;

    @NotNull
    @Size(min = 1)
    private List<CertificateDto> certificates = new ArrayList<>();
}
