package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.util.DateConverter;
import com.epam.esm.util.DbcpManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Child class of {@link AbstractRepository} interface
 * Provides CRUD and search operations for Certificate entities
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Component
public class CertificateRepository implements AbstractRepository<Certificate> {

    private final static String SQL_SAVE_CERTIFICATE =
            "insert into certificates (name, description, price, create_date, duration) values (?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE_CERTIFICATE =
            "update certificates set name = ?, description = ?, price = ?, last_update_date = ?, duration = ? " +
                    "where id_certificate = ?;";
    private final static String SQL_DELETE_CERTIFICATE = "delete from certificates where id_certificate = ?;";
    private final static String SQL_FIND_CERTIFICATE = "select id_certificate, name, description, price, " +
            "create_date, last_update_date, duration from certificates where id_certificate = ?;";
    private final static String SQL_SAVE_CERTIFICATE_TAG =
            "insert into certificate_tag (id_certificate, id_tag) values (?, ?);";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Certificate> rowMapper;
    private final DateConverter dateConverter;
    private final DbcpManager dbcpManager;

    @Autowired
    public CertificateRepository(JdbcTemplate jdbcTemplate,
                                 RowMapper<Certificate> rowMapper,
                                 DateConverter dateConverter,
                                 DbcpManager dbcpManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbcpManager = dbcpManager;
        this.rowMapper = rowMapper;
        this.dateConverter = dateConverter;
    }

    @Override
    public Certificate save(Certificate certificate) throws DaoException {
        assert certificate.getId() != null || jdbcTemplate.update
                (
                        SQL_SAVE_CERTIFICATE, certificate.getName(),
                        certificate.getDescription(), certificate.getPrice(),
                        dateConverter.convertSqlTimestampFromDate(certificate.getCreateDate()),
                        certificate.getDuration()
                ) > 0;
        return findEntityById(certificate.getId());
    }

    public void saveTag(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_SAVE_CERTIFICATE_TAG, certificateId, tagId);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SQL_DELETE_CERTIFICATE, id) == 1;
    }

    @Override
    public Certificate update(Certificate certificate) throws DaoException {
        if (certificate.getId() != null) {
            assert jdbcTemplate.update
                    (
                            SQL_UPDATE_CERTIFICATE, certificate.getName(),
                            certificate.getDescription(), certificate.getPrice(),
                            dateConverter.convertSqlTimestampFromDate(certificate.getLastUpdateDate()),
                            certificate.getDuration(), certificate.getId()
                    ) > 0;
            return findEntityById(certificate.getId());
        } else {
            log.debug("Certificate not found for update: id is null");
            throw new DaoException("Certificate not found for update");
        }
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE, rowMapper);
    }

    @Override
    public Certificate findEntityById(long id) throws DaoException {
        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(SQL_FIND_CERTIFICATE, new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            log.debug("Couldn't find entity of type Certificate with id {}", id);
            throw new DaoException(e);
        }
        return certificate;
    }

}
