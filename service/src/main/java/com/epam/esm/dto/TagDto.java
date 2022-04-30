package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagDto extends AbstractDto {
    private String name;

}
