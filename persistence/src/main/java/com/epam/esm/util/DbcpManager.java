package com.epam.esm.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Class for forming application data source
 * @author Marianna Patrusova
 * @version 1.0
 */
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

    /**
     * Method: forms data source using application DB properties.
     * @return singleton instance of {@link DataSource}
     */
    public DataSource getDataSource() throws ClassNotFoundException {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);
        String user = bundle.getString(USER);
        String pass = bundle.getString(PASS);
        String url = bundle.getString(URL);
        String driver = bundle.getString(DRIVER);
        int minIdle = Integer.parseInt(bundle.getString(MIN_IDLE));
        int maxIdle = Integer.parseInt(bundle.getString(MAX_IDLE));
        int maxPs = Integer.parseInt(bundle.getString(MAX_PS));

        Class.forName(driver);
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
