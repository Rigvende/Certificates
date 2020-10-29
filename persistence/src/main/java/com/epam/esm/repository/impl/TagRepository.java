package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Child class of {@link CrudRepository} interface
 * Provides CRD and search operations for Tag entities
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Repository
public class TagRepository extends AbstractRepository<Tag> {

    private final static String SQL_SAVE_TAG = "insert into tags (name) values (?);";
    private final static String SQL_FIND_TAG_BY_NAME = "select id_tag, name from tags where name = ?;";
    private final static String SQL_FIND_BY_CERTIFICATE = "select t.id_tag, t.name from tags t join certificate_tag ct " +
            "on t.id_tag = ct.id_tag where ct.id_certificate = ?;";

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.rowMapper = tagMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = "tags";
        this.tableId = "id_tag";
    }

    /**
     * Method: save one entity in database.
     * @param  tag: instance of {@link Tag} entity
     * @throws DaoException if data access failed while finding saved entity in database
     * @return saved instance of {@link Tag}
     */
    @Override
    public Tag save(Tag tag) throws DaoException {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_SAVE_TAG, new String[] {"id_tag"});
                    ps.setString(1, tag.getName());
                    return ps;
                }, key);
        Long id = Objects.requireNonNull(key.getKey()).longValue();
        return findEntityById(id);
    }

    /**
     * Method: update one entity in database.
     * @param  tag: instance of {@link Tag} entity
     * @throws UnsupportedOperationException because this operation not supported for tag
     * @return updated entity
     */
    @Override
    public Tag update(Tag tag) {
        log.warn("Attempt to update tag {}", tag);
        throw new UnsupportedOperationException("Tag update is unsupported");
    }

    /**
     * Method: find tag in database by its name.
     * @param  name: tag name
     * @return found {@link Tag} instance
     */
    public List<Tag> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_NAME, new Object[]{name}, rowMapper);
    }

    /**
     * Method: find tags by related certificate id.
     * @param  id: certificate id
     * @return list of {@link Tag} instances
     */
    public List<Tag> findAllByCertificate(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_CERTIFICATE, new Object[]{id}, rowMapper);
    }

}
