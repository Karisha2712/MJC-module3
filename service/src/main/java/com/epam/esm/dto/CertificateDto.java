package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateDto extends AbstractDto {
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private String createdDate;
    private String lastUpdateDate;
    private List<TagDto> tags;
}
