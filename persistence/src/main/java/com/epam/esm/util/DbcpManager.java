package com.epam.esm.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Class for forming application data source
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@Component
public class DbcpManager {

    private final static String USER = "user";
    private final static String PASS = "pass";
    private final static String URL = "url";
    private final static String DRIVER = "driver";
    private final static String MIN_IDLE = "min.idle";
    private final static String MAX_IDLE = "max.idle";
    private final static String MAX_PS = "max.open.prepared.statements";
    private final static String BUNDLE = "connectionDB";
    private final DataSource dataSource;

    public DbcpManager() {
        this.dataSource = createDataSource();
    }

    /**
     * Method: returns data source according with application DB properties.
     * @return instance of {@link DataSource}
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    private DataSource createDataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);
        String user = bundle.getString(USER);
        String pass = bundle.getString(PASS);
        String url = bundle.getString(URL);
        String driver = bundle.getString(DRIVER);
        int minIdle = Integer.parseInt(bundle.getString(MIN_IDLE));
        int maxIdle = Integer.parseInt(bundle.getString(MAX_IDLE));
        int maxPs = Integer.parseInt(bundle.getString(MAX_PS));
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Driver class not found");
        }
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setMinIdle(minIdle);
        ds.setMaxIdle(maxIdle);
        ds.setMaxOpenPreparedStatements(maxPs);

        return ds;
    }

}
