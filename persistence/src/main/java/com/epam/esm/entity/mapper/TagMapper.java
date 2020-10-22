package com.epam.esm.entity.mapper;

import com.epam.esm.entity.impl.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping table row to {@link Tag} instance
 * @author Marianna Patrusova
 * @version 1.0
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    /**
     * Method: convert table row to {@link Tag}
     * @param resultSet: instance of {@link ResultSet}
     * @param i: number of row
     * @return instance of {@link Tag}
     */
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Tag(resultSet.getLong("id_tag"), resultSet.getString("name"));
    }

}
