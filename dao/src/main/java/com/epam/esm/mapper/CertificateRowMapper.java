package com.epam.esm.mapper;


import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.mapper.ColumnName.*;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getLong(ID));
        certificate.setTitle(rs.getString(CERTIFICATE_TITLE));
        certificate.setPrice(rs.getBigDecimal(CERTIFICATE_PRICE));
        certificate.setDescription(rs.getString(CERTIFICATE_DESCRIPTION));
        certificate.setDuration(rs.getInt(CERTIFICATE_DURATION));
        certificate.setCreatedDate(rs.getTimestamp(CERTIFICATE_CREATED_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp(CERTIFICATE_LAST_UPDATED_DATE).toLocalDateTime());
        return certificate;
    }
}
