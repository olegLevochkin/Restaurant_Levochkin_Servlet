package com.example.restaurant.model.dao.imp;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

class JDBCConnectionManager {

    private static volatile DataSource dataSource;

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        URL = resourceBundle.getString("database.url");
        USER = resourceBundle.getString("database.user");
        PASSWORD = resourceBundle.getString("database.password");
    }

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (JDBCConnectionManager.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(URL);
                    ds.setUsername(USER);
                    ds.setPassword(PASSWORD);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
