package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Abstract class for entity repositories
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Repository
public abstract class AbstractRepository<T extends AbstractEntity> implements CrudRepository<T> {

    private final static String SQL_FIND_ONE = "SELECT * FROM %s";
    private final static String SQL_FIND_ALL = "SELECT * FROM %s WHERE %s = ?";
    private final static String SQL_DELETE_ONE = "DELETE FROM %s WHERE %s = ?";
    protected RowMapper<T> rowMapper;
    protected JdbcTemplate jdbcTemplate;
    protected String tableName;
    protected String tableId;

    /**
     * Method: find all entities in database.
     * @return list of {@link AbstractEntity} child instances
     */
    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(String.format(SQL_FIND_ONE, tableName), rowMapper);
    }

    /**
     * Method: find one entity in database by id.
     * @param  id: entity id
     * @throws DaoException if data access failed
     * @return instance of {@link AbstractEntity} child
     */
    @Override
    public T findEntityById(long id) throws DaoException {
        T t;
        try {
            t = jdbcTemplate.queryForObject(String.format(SQL_FIND_ALL, tableName, tableId),
                    new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            log.error("Data access failed while finding entity by id {}", id);
            throw new DaoException(e);
        }
        return t;
    }

    /**
     * Method: delete one entity in database by id.
     * @param  id: entity id
     * @return boolean true if operation is successful
     */
    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(String.format(SQL_DELETE_ONE, tableName, tableId), id) == 1;
    }

}
