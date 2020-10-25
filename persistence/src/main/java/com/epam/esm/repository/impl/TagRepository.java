package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    private final static String SQL_FIND_TAG_BY_NAME = "select id, name from tags where name = ?;";

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
        assert tag.getId() != null || jdbcTemplate.update(SQL_SAVE_TAG, tag.getName()) > 0;
        return findEntityById(tag.getId());
    }

    /**
     * Method: update one entity in database.
     * @param  tag: instance of {@link Tag} entity
     * @throws UnsupportedOperationException because this operation not supported for tag
     * @return updated entity
     */
    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject(SQL_FIND_TAG_BY_NAME, new Object[]{name}, rowMapper);
    }

}
