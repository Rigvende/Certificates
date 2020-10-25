package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.util.DateConverter;
import com.epam.esm.util.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Child class of {@link CrudRepository} interface
 * Provides CRUD and search operations for Certificate entities
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Repository
public class CertificateRepository extends AbstractRepository<Certificate> {

    private final static String SQL_SAVE_CERTIFICATE =
            "insert into certificates (name, description, price, create_date, duration) values (?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE_CERTIFICATE =
            "update certificates set name = ?, description = ?, price = ?, last_update_date = ?, duration = ? " +
                    "where id_certificate = ?;";
    private final static String SQL_SAVE_CERTIFICATE_TAG =
            "insert into certificate_tag (id_certificate, id_tag) values (?, ?);";
    private final static String SQL_DELETE_CERTIFICATE_TAG =
            "delete from certificate_tag where id_tag = ? and id_certificate = ?";
    private final static String SQL_FIND_BY_TAG =
            "select c.id_certificate, c.name, c.description, c.price, c.create_date, c.last_update_date, c.duration " +
                    "from certificates c join certificate_tag ct on c.id_certificate = ct.id_certificate " +
                    "join tags t on ct.id_tag = t.id_tag where t.name = ?;";

    @Autowired
    public CertificateRepository(CertificateMapper certificateMapper,
                                 JdbcTemplate jdbcTemplate) {
        this.rowMapper = certificateMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = "certificates";
        this.tableId = "id_certificate";
    }

    /**
     * Method: save one entity in database.
     * @param  certificate: instance of {@link Certificate} entity
     * @throws DaoException if data access failed while finding saved entity in database
     * @return saved instance of {@link Certificate}
     */
    @Override
    public Certificate save(Certificate certificate) throws DaoException {
        assert certificate.getId() != null || jdbcTemplate.update
                (
                        SQL_SAVE_CERTIFICATE, certificate.getName(),
                        certificate.getDescription(), certificate.getPrice(),
                        offsetToTimestamp(certificate.getCreateDate()),
                        certificate.getDuration()
                ) > 0;
        return findEntityById(certificate.getId());
    }

    /**
     * Method: update one entity in database.
     * @param  certificate: instance of {@link Certificate} entity
     * @throws DaoException if data access failed while finding updated entity in database
     * @return updated instance of {@link Certificate}
     */
    @Override
    public Certificate update(Certificate certificate) throws DaoException {
        assert certificate.getId() != null && jdbcTemplate.update
                    (
                            SQL_UPDATE_CERTIFICATE, certificate.getName(),
                            certificate.getDescription(), certificate.getPrice(),
                            offsetToTimestamp(certificate.getLastUpdateDate()),
                            certificate.getDuration(), certificate.getId()
                    ) > 0;
        return findEntityById(certificate.getId());
    }

    /**
     * Method: save many-to-many connection between tag and certificate in the cross database table.
     * @param  certificateId: certificate id
     * @param tagId: tag id
     */
    public void saveCertificateTagLink(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_SAVE_CERTIFICATE_TAG, certificateId, tagId);
    }

    /**
     * Method: delete many-to-many connection between tag and certificate in the cross database table.
     * @param  certificateId: certificate id
     * @param tagId: tag id
     */
    public void deleteCertificateTagLink(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, tagId);
    }

    /**
     * Method: find all certificates by related tag name in database.
     * @param  tagName: tag name
     * @return list of {@link Certificate}
     */
    public List<Certificate> findAllByTag(String tagName) {
        return jdbcTemplate.query(SQL_FIND_BY_TAG, new Object[]{tagName}, rowMapper);
    }

    private Timestamp offsetToTimestamp(OffsetDateTime offsetDateTime) {
        LocalDateTime localDateTime = DateFormatter.formatDateToLocal(offsetDateTime);
        return DateConverter.convertSqlTimestampFromDate(localDateTime);
    }

}
