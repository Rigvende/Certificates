package com.epam.esm.entity.mapper;

import com.epam.esm.entity.impl.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Tag(resultSet.getLong("id_tag"), resultSet.getString("name"));
    }

}
