package com.epam.esm.mapper;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.entity.BaseEntity;

public interface DtoMapper<E extends BaseEntity, D extends AbstractDto> {
    D mapToDto(E entity);

    E mapToEntity(D dto);
}

