package com.epam.esm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Objects;

@Slf4j
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@PropertySource("classpath:connectionDB.properties")
public class DataSourceConfig {

    @Value("${db.driver}")
    private String DRIVER;

    @Value("${db.url}")
    private String URL;

    @Value("${db.user}")
    private String USER;

    @Value("${db.pass}")
    private String PASS;

    @Value("${db.min.idle}")
    private String MIN_IDLE;

    @Value("${db.max.idle}")
    private String MAX_IDLE;

    @Value("${db.max.open.prepared.statements}")
    private String MAX_PS;

    private final Environment env;

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty(DRIVER));
        ds.setUrl(env.getProperty(URL));
        ds.setUsername(env.getProperty(USER));
        ds.setPassword(env.getProperty(PASS));
        ds.setMinIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty(MIN_IDLE))));
        ds.setMaxIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty(MAX_IDLE))));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(Objects.requireNonNull(env.getProperty(MAX_PS))));
        return ds;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
