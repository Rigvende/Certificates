package com.epam.esm.entity.mapper;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.util.DateConverter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping table row to {@link Certificate} instance
 * @author Marianna Patrusova
 * @version 1.0
 */
@Component
public class CertificateMapper implements RowMapper<Certificate> {

    /**
     * Method: convert table row to {@link Certificate}
     * @param resultSet: instance of {@link ResultSet}
     * @param i: number of row
     * @return instance of {@link Certificate}
     */
    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Certificate(resultSet.getLong("id_certificate"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getBigDecimal("price"),
                DateConverter.convertDateFromSqlTimestamp(resultSet.getTimestamp("create_date")),
                DateConverter.convertDateFromSqlTimestamp(resultSet.getTimestamp("last_update_date")),
                resultSet.getInt("duration"));
    }

}
