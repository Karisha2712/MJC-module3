package com.epam.esm.pagination;

import com.epam.esm.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Page<T extends AbstractDto> extends RepresentationModel<Page<T>> {
    private Integer currentPage;
    private Integer totalPageNumber;
    private Integer elementsPerPageNumber;
    private List<T> pageContent;
}
