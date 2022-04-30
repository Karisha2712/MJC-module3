package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.mapper.ColumnName.ID;
import static com.epam.esm.mapper.ColumnName.TAG_NAME;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(ID));
        tag.setName(rs.getString(TAG_NAME));
        return tag;
    }
}
