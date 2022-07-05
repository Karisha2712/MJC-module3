package com.epam.esm.filter;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CertificatesFilter implements Specification<Certificate> {
    private static final String TITLE = "title";
    private static final String DATE = "createdDate";
    private static final String ID = "id";
    private static final String PERCENT = "%";
    private static final String TAGS = "tags";
    private static final String TAG_NAME = "name";
    private static final String IS_DELETED = "isDeleted";

    private String sortBy;
    private Order order;
    private String textPart;
    private Set<String> tagNames;

    public boolean isFilterParamsNotValid() {
        return sortBy != null && !sortBy.equals(TITLE) && !sortBy.equals(DATE) && !sortBy.equals(ID);
    }

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (sortBy == null || (!sortBy.equals(TITLE) && !sortBy.equals(DATE))) {
            sortBy = ID;
        }
        if (order == Order.DESC) {
            query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
        } else {
            query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
        }
        return applyPredicates(criteriaBuilder, root);
    }

    private Predicate applyPredicates(CriteriaBuilder criteriaBuilder, Root<Certificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (textPart != null) {
            predicates.add(criteriaBuilder.like(root.get(TITLE), PERCENT + textPart + PERCENT));
        }
        if (tagNames != null) {
            Set<String> tags = tagNames;
            tags.forEach(name -> {
                ListJoin<Certificate, Tag> tagsJoin = root.joinList(TAGS);
                predicates.add(criteriaBuilder.equal(tagsJoin.get(TAG_NAME), name));
            });
        }
        predicates.add(criteriaBuilder.isFalse(root.get(IS_DELETED)));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public enum Order {
        ASC, DESC
    }
}
