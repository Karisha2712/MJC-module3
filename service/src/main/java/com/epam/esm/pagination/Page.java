package com.epam.esm.pagination;

import com.epam.esm.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T extends AbstractDto> {
    private Integer currentPage;
    private Integer totalPageNumber;
    private Integer elementsPerPageNumber;
    private List<T> pageContent;
}
