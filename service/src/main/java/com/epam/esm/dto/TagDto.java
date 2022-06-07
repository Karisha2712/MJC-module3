package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagDto extends AbstractDto {
    @NotNull
    @Size(min = 1)
    private String name;
}
