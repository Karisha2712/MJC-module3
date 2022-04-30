package com.epam.esm.mapper;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.entity.AbstractEntity;

public interface DtoMapper<E extends AbstractEntity, D extends AbstractDto> {
    D mapToDto(E entity);

    E mapToEntity(D dto);
}

