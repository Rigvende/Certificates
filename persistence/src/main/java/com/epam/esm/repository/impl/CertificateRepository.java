package com.epam.esm.repository.impl;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.util.DateConverter;
import com.epam.esm.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Child class of {@link CrudRepository} interface
 * Provides CRUD and search operations for Certificate entities
 * @author Marianna Patrusova
 * @version 1.0
 */
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
    private final static String SQL_FIND_BY_NAME = "select * from certificates where name like ?;";
    private final static String SQL_FIND_BY_DESCRIPTION = "select * from certificates where description like ?;";


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
     * @param certificate: instance of {@link Certificate} entity
     * @return saved instance of {@link Certificate}
     * @throws DaoException if data access failed while finding saved entity in database
     */
    @Override
    public Certificate save(Certificate certificate) throws DaoException {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update
                (
                        connection -> {
                            PreparedStatement ps =
                                    connection.prepareStatement(SQL_SAVE_CERTIFICATE, new String[]{"id_certificate"});
                            ps.setString(1, certificate.getName());
                            ps.setString(2, certificate.getDescription());
                            ps.setBigDecimal(3, certificate.getPrice());
                            ps.setTimestamp(4, offsetToTimestamp(certificate.getCreateDate()));
                            ps.setInt(5, certificate.getDuration());
                            return ps;
                        }, key
                );
        Long id = Objects.requireNonNull(key.getKey()).longValue();
        return findEntityById(id);
    }

    /**
     * Method: update one entity in database.
     * @param certificate: instance of {@link Certificate} entity
     * @return updated instance of {@link Certificate}
     * @throws DaoException if data access failed while finding updated entity in database
     */
    @Override
    public Certificate update(Certificate certificate) throws DaoException {
        jdbcTemplate.update
                (
                        SQL_UPDATE_CERTIFICATE, certificate.getName(),
                        certificate.getDescription(), certificate.getPrice(),
                        offsetToTimestamp(certificate.getLastUpdateDate()),
                        certificate.getDuration(), certificate.getId()
                );
        return findEntityById(certificate.getId());
    }

    /**
     * Method: save many-to-many connection between tag and certificate in the cross database table.
     * @param certificateId: certificate id
     * @param tagId: tag id
     */
    public void saveCertificateTagLink(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_SAVE_CERTIFICATE_TAG, certificateId, tagId);
    }

    /**
     * Method: delete many-to-many connection between tag and certificate in the cross database table.
     * @param certificateId: certificate id
     * @param tagId: tag id
     */
    public void deleteCertificateTagLink(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE_TAG, certificateId, tagId);
    }

    /**
     * Method: find all certificates by related tag name in database.
     * @param tagName: tag name
     * @return list of {@link Certificate}
     */
    public List<Certificate> findAllByTag(String tagName) {
        return jdbcTemplate.query(SQL_FIND_BY_TAG, new Object[]{tagName}, rowMapper);
    }

    /**
     * Method: find all certificates by name in database.
     * @param name: certificate name or part of name
     * @return list of {@link Certificate}
     */
    public List<Certificate> findAllByName(String name) {
        name = "%" + name + "%";
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new Object[]{name}, rowMapper);
    }

    /**
     * Method: find all certificates by description in database.
     * @param description: certificate description or part of description
     * @return list of {@link Certificate}
     */
    public List<Certificate> findAllByDescription(String description) {
        description = "%" + description + "%";
        return jdbcTemplate.query(SQL_FIND_BY_DESCRIPTION, new Object[]{description}, rowMapper);
    }

    private Timestamp offsetToTimestamp(OffsetDateTime offsetDateTime) {
        LocalDateTime localDateTime = DateFormatter.formatOffsetDateTimeToLocal(offsetDateTime);
        return DateConverter.convertSqlTimestampFromDate(localDateTime);
    }

}
