package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Tag extends AbstractEntity {
    private String name;
}
