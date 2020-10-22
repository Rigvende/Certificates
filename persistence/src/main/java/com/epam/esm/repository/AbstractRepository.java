package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Abstract class for entity repositories
 * @author Marianna Patrusova
 * @version 1.0
 */
public abstract class AbstractRepository<T extends AbstractEntity> implements CrudRepository<T> {

    protected TagMapper tagMapper;
    protected CertificateMapper certificateMapper;
    protected JdbcTemplate jdbcTemplate;

}
