package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.mapper.TableField.*;

@Repository
@AllArgsConstructor
public class CertificateDaoImpl implements CertificateDao {
    private static final String GET_SINGLE_CERTIFICATE_QUERY =
            "SELECT * FROM certificates WHERE id = ?";
    private static final String INSERT_CERTIFICATE_QUERY =
            "INSERT INTO certificates (title, description, duration, price, created_date, last_updated_date) " +
                    "values(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_CERTIFICATE_QUERY =
            "DELETE FROM certificates WHERE id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE certificates SET %s WHERE id = ?";
    private static final String BASE_QUERY_PART =
            "SELECT * FROM certificates ";
    private static final String FIND_CERTIFICATES_BY_TAG_QUERY_PART =
            "JOIN certificates_has_tags ON certificates.id = certificates_id " +
                    "JOIN tags ON tags_id = tags.id WHERE name = ? ";
    private static final String FIND_CERTIFICATES_BY_TITLE_OR_DESCRIPTION_QUERY_PART =
            "(title LIKE ? OR description LIKE ?) ";
    private static final String SORT_CERTIFICATES_QUERY_PART = "ORDER BY %s %s ";
    private static final String AND_KEY_WORD = "AND ";
    private static final String WHERE_KEY_WORD = "WHERE ";
    private static final String DEFAULT_ORDER = "ASC";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Certificate> certificateRowMapper;

    @Override
    public Optional<Certificate> receiveEntityById(long id) {
        return jdbcTemplate.query(GET_SINGLE_CERTIFICATE_QUERY, certificateRowMapper, id).stream().findAny();
    }

    @Override
    public List<Certificate> receiveCertificatesByCriteria(String tagName, String textPart,
                                                           String sortBy, String order) {
        return jdbcTemplate.query(createGetQuery(tagName, textPart, sortBy, order), certificateRowMapper,
                receiveValuesForGetQuery(tagName, textPart));
    }

    @Override
    public long insertEntity(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_CERTIFICATE_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getTitle());
            ps.setString(2, certificate.getDescription());
            ps.setLong(3, certificate.getDuration());
            ps.setBigDecimal(4, certificate.getPrice());
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreatedDate()));
            ps.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void updateCertificate(long id, Certificate certificate) {
        Object[] updatingValues = receiveUpdatingValues(id, certificate);
        String updatingFields = receiveUpdatingFields(certificate);
        String query = String.format(UPDATE_QUERY, updatingFields);
        jdbcTemplate.update(query, updatingValues);
    }

    @Override
    public void deleteEntity(long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_QUERY, id);
    }


    private String createGetQuery(String tagName, String textPart,
                                  String sortBy, String order) {
        if (order == null) {
            order = DEFAULT_ORDER;
        }
        StringBuilder result = new StringBuilder();
        result.append(BASE_QUERY_PART);
        if (tagName != null) {
            result.append(FIND_CERTIFICATES_BY_TAG_QUERY_PART);
        }
        if (textPart != null) {
            result.append(tagName == null ? WHERE_KEY_WORD : AND_KEY_WORD);
            result.append(FIND_CERTIFICATES_BY_TITLE_OR_DESCRIPTION_QUERY_PART);
        }
        if (sortBy != null) {
            result.append(String.format(SORT_CERTIFICATES_QUERY_PART, sortBy, order));
        }
        return result.toString();
    }

    private Object[] receiveValuesForGetQuery(String tagName, String textPart) {
        List<Object> result = new ArrayList<>();
        if (tagName != null) {
            result.add(tagName);
        }
        if (textPart != null) {
            textPart = '%' + textPart + '%';
            result.add(textPart);
            result.add(textPart);
        }
        return result.toArray();
    }

    private Object[] receiveUpdatingValues(long id, Certificate certificate) {
        ArrayList<Object> result = new ArrayList<>();
        if (certificate.getTitle() != null) {
            result.add(certificate.getTitle());
        }
        if (certificate.getDuration() != null) {
            result.add(certificate.getDuration());
        }
        if (certificate.getPrice() != null) {
            result.add(certificate.getPrice());
        }
        if (certificate.getDescription() != null) {
            result.add(certificate.getDescription());
        }
        result.add(LocalDateTime.now());
        result.add(id);
        return result.toArray();
    }

    private String receiveUpdatingFields(Certificate certificate) {
        StringBuilder result = new StringBuilder();
        if (certificate.getTitle() != null) {
            result.append(String.format("%s = ?, ", TITLE));
        }
        if (certificate.getDuration() != null) {
            result.append(String.format("%s = ?, ", DURATION));
        }
        if (certificate.getPrice() != null) {
            result.append(String.format("%s = ?, ", PRICE));
        }
        if (certificate.getDescription() != null) {
            result.append(String.format("%s = ?, ", DESCRIPTION));
        }
        result.append(String.format("%s = ? ", LAST_UPDATED_DATE));
        return result.toString();
    }

}
