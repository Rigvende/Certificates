package com.epam.esm.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * Class for forming application data source
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Component
@EnableTransactionManagement
@PropertySource("classpath:connectionDB.properties")
public class DbcpManager {

    @Value("${db.user}")
    private String USER;

    @Value("${db.pass")
    private String PASS;

    @Value("${db.url")
    private String URL;

    @Value("${db.driver")
    private String DRIVER;

    @Value("${db.min.idle")
    private String MIN_IDLE;

    @Value("${db.max.idle")
    private String MAX_IDLE;

    @Value("${db.max.open.prepared.statements")
    private String MAX_PS;

    /**
     * Method: returns data source according with application DB properties.
     * @return instance of {@link DataSource}
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("Driver class not found");
        }
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(URL);
        ds.setUsername(USER);
        ds.setPassword(PASS);
        ds.setMinIdle(Integer.parseInt(MIN_IDLE));
        ds.setMaxIdle(Integer.parseInt(MAX_IDLE));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(MAX_PS));
        return ds;
    }

}
