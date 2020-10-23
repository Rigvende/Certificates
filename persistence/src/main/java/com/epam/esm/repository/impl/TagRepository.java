package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.exception.DaoException;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

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
    private final static String SQL_DELETE_TAG = "delete from tags where id_tag = ?;";
    private final static String SQL_FIND_TAG = "select id_tag, name from tags where id_tag = ?;";

    @Autowired
    public TagRepository(DataSource dataSource,
                         TagMapper tagMapper) {
        this.tagMapper = tagMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Tag save(Tag tag) throws DaoException {
        assert tag.getId() != null || jdbcTemplate.update(SQL_SAVE_TAG, tag.getName()) > 0;
        return findEntityById(tag.getId());
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SQL_DELETE_TAG, id) == 1;
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_TAG, tagMapper);
    }

    @Override
    public Tag findEntityById(long id) throws DaoException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(SQL_FIND_TAG, new Object[]{id}, tagMapper);
        } catch (DataAccessException e) {
            log.debug("Couldn't find entity of type Tag with id {}", id);
            throw new DaoException(e);
        }
        return tag;
    }

}
