package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.exception.DaoException;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Interface for CRUD actions on the persistence layer
 * @author Marianna Patrusova
 * @version 1.0
 */
@Repository
public interface CrudRepository<T> {

    /**
     * Method: create database table's row depending on AbstractEntity instance.
     * @param entity is type of {@link AbstractEntity} children
     * @return long (generated id of new db row)
     * @throws DaoException object
     */
    T save(T entity) throws DaoException;

    /**
     * Method: delete database table's row depending on AbstractEntity instance.
     * @param id is table's id of deleting entity
     * @return boolean
     */
    boolean delete(long id);

    /**
     * Method: update database table's row depending on AbstractEntity instance.
     * @param entity is type of {@link AbstractEntity} children
     * @return instance of {@link AbstractEntity} children
     * @throws DaoException object
     */
    T update(T entity) throws DaoException;

    /**
     * Method: find all rows in database table depending on method's realization
     * and create suitable AbstractEntity objects.
     * @return List of found T ({@link AbstractEntity} children) type objects
     */
    List<T> findAll();

    /**
     * Method: find suitable row in database table for creating AbstractEntity instance
     * @param id is table's id of object
     * @return type T of {@link AbstractEntity}
     * @throws DaoException object
     */
    T findEntityById(long id) throws DaoException;
}
