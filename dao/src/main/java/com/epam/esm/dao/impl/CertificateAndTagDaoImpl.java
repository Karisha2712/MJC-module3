package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateAndTagDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CertificateAndTagDaoImpl implements CertificateAndTagDao {
    private static final String INSERT_QUERY =
            "INSERT INTO certificates_has_tags values (?, ?)";
    private static final String DELETE_QUERY =
            "DELETE FROM certificates_has_tags " +
                    "WHERE certificates_id = ? AND tags_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertRelationship(long certificateId, long tagId) {
        jdbcTemplate.update(INSERT_QUERY, certificateId, tagId);
    }

    @Override
    public void deleteRelationship(long tagId, long certificateId) {
        jdbcTemplate.update(DELETE_QUERY, certificateId, tagId);
    }
}
