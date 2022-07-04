package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag> {
    String SELECT_MOST_WIDELY_USED_TAG_QUERY =
            "SELECT tags.id, tags.name from (orders \n" +
                    "JOIN orders_has_certificates ON orders.id = orders_has_certificates.orders_id \n" +
                    "JOIN certificates ON orders_has_certificates.certificates_id = certificates.id \n" +
                    "JOIN certificates_has_tags ON certificates.id = certificates_has_tags.certificates_id\n" +
                    "JOIN tags ON certificates_has_tags.tags_id = tags.id)\n" +
                    "WHERE users_id =\n" +
                    "(SELECT users_id from orders GROUP BY users_id ORDER BY sum(cost) DESC LIMIT 1)\n" +
                    "GROUP BY tags_id \n" +
                    "ORDER BY count(tags_id) DESC\n" +
                    "LIMIT 1";

    Optional<Tag> findByName(String name);

    @Query(nativeQuery = true, value = SELECT_MOST_WIDELY_USED_TAG_QUERY)
    Optional<Tag> findMostWidelyUsedTag();
}
