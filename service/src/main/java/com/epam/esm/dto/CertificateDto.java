package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CertificateDto extends AbstractDto {
    @NotNull
    @Size(min = 1)
    private String title;

    @NotNull
    @Size(min = 1)
    private String description;

    @NotNull
    @Positive
    private Integer duration;

    @NotNull
    @Positive
    private BigDecimal price;

    private String createdDate;
    private String lastUpdateDate;
    private List<TagDto> tags;
}
