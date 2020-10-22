package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.mapper.CertificateMapper;
import com.epam.esm.exception.DaoException;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.util.DateConverter;
import com.epam.esm.util.DbcpManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Child class of {@link AbstractRepository} interface
 * Provides CRUD and search operations for Certificate entities
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Repository
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

    private final CertificateMapper certificateMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateRepository(CertificateMapper certificateMapper,
                                 DbcpManager dbcpManager) {
        this.certificateMapper = certificateMapper;
        jdbcTemplate = new JdbcTemplate(dbcpManager.getDataSource());
    }

    @Override
    public Certificate save(Certificate certificate) throws DaoException {
        assert certificate.getId() != null || jdbcTemplate.update
                (
                        SQL_SAVE_CERTIFICATE, certificate.getName(),
                        certificate.getDescription(), certificate.getPrice(),
                        DateConverter.convertSqlTimestampFromDate(certificate.getCreateDate()),
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
        assert certificate.getId() != null && jdbcTemplate.update
                    (
                            SQL_UPDATE_CERTIFICATE, certificate.getName(),
                            certificate.getDescription(), certificate.getPrice(),
                            DateConverter.convertSqlTimestampFromDate(certificate.getLastUpdateDate()),
                            certificate.getDuration(), certificate.getId()
                    ) > 0;
        return findEntityById(certificate.getId());
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE, certificateMapper);
    }

    @Override
    public Certificate findEntityById(long id) throws DaoException {
        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(SQL_FIND_CERTIFICATE, new Object[]{id}, certificateMapper);
        } catch (DataAccessException e) {
            log.debug("Couldn't find entity of type Certificate with id {}", id);
            throw new DaoException(e);
        }
        return certificate;
    }

}
