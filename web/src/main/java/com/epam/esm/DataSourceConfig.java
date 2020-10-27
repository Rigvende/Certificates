package com.epam.esm;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * Class for data source and related beans configuration
 * @author Marianna Patrusova
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@PropertySource("classpath:connectionDB.properties")
public class DataSourceConfig {

    private final Environment env;

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("db.driver"));
        ds.setUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.user"));
        ds.setPassword(env.getProperty("db.pass"));
        ds.setMinIdle(Integer.parseInt(
                Objects.requireNonNull(env.getProperty("db.min.idle"))));
        ds.setMaxIdle(Integer.parseInt(
                Objects.requireNonNull(env.getProperty("db.max.idle"))));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(
                Objects.requireNonNull(env.getProperty("db.max.open.prepared.statements"))));
        return ds;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
