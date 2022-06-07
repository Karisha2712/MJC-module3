package com.epam.esm.hateoas;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.pagination.Page;

public interface LinksCreator<T extends AbstractDto> {
    void createLinks(T entity);

    void createPaginationLinks(Page<T> page);
}
