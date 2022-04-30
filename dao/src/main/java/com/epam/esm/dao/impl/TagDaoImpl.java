package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String GET_ALL_TAGS_QUERY =
            "SELECT * FROM tags";
    private static final String GET_SINGLE_TAG_QUERY =
            "SELECT * FROM tags WHERE id = ?";
    private static final String GET_TAG_BY_NAME_QUERY =
            "SELECT * FROM tags WHERE name = ?";
    private static final String INSERT_TAG_QUERY =
            "INSERT INTO tags (name) values (?)";
    private static final String DELETE_TAG_QUERY =
            "DELETE FROM tags WHERE id = ?";
    private static final String GET_CERTIFICATE_TAGS_QUERY =
            "SELECT id, name FROM certificates_has_tags " +
                    "JOIN tags ON tags_id = id WHERE certificates_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Tag> tagRowMapper;

    @Override
    public List<Tag> receiveAllTags() {
        return jdbcTemplate.query(GET_ALL_TAGS_QUERY, tagRowMapper);
    }

    @Override
    public Optional<Tag> receiveEntityById(long id) {
        return jdbcTemplate.query(GET_SINGLE_TAG_QUERY, tagRowMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Tag> receiveTagByName(String name) {
        return jdbcTemplate.query(GET_TAG_BY_NAME_QUERY, tagRowMapper, name).stream().findAny();
    }

    @Override
    public List<Tag> receiveCertificateTags(long id) {
        return jdbcTemplate.query(GET_CERTIFICATE_TAGS_QUERY, tagRowMapper, id);
    }

    @Override
    public void deleteEntity(long id) {
        jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }

    @Override
    public long insertEntity(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TAG_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
