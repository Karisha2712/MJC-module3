package com.epam.esm.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CertificatesFilter {
    private static final String TITLE = "title";
    private static final String DATE = "createdDate";
    private static final String ID = "id";
    private String sortBy;
    private Order order;
    private String textPart;
    private Set<String> tagNames;

    public boolean isFilterParamsNotValid() {
        return sortBy != null && !sortBy.equals(TITLE) && !sortBy.equals(DATE) && !sortBy.equals(ID);
    }

    public enum Order {
        ASC, DESC
    }
}
