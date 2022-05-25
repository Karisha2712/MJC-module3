package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDto extends RepresentationModel<AbstractDto> {
    private Long id;
}
