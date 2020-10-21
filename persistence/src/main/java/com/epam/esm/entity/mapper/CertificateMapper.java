package com.epam.esm.entity.mapper;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CertificateMapper implements RowMapper<Certificate> {

    private final DateConverter converter;

    @Autowired
    public CertificateMapper(DateConverter converter) {
        this.converter = converter;
    }

    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Certificate(resultSet.getLong("id_certificate"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getBigDecimal("price"),
                converter.convertDateFromSqlTimestamp(resultSet.getTimestamp("create_date")),
                converter.convertDateFromSqlTimestamp(resultSet.getTimestamp("last_update_date")),
                resultSet.getInt("duration"));
    }

}
