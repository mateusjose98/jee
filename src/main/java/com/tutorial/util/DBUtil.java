package com.tutorial.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private final static String URL = "jdbc:mysql://localhost:3306/db_jee?serverTimezone=UTC";
    private final static String USER = "user";
    private final static String PASSWORD = "pass";
    private static BasicDataSource pool;

    public static BasicDataSource getConfiguredPool() {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(URL);
            pool.setUsername(USER);
            pool.setPassword(PASSWORD);
            // O número inicial de conexões a serem criadas quando o pool é inicializado, default 0
            pool.setInitialSize(3);
            // O número mínimo de conexões inativas(aguardando) a serem mantidas no pool.
            pool.setMinIdle(3);
            // O número máximo de conexões inativas(aguardando) a serem mantidas no pool.
            pool.setMaxIdle(8);
            // The maximum number of active connections that can be allocated from this pool at the same time, or negative for no limit., default 8
            pool.setMaxTotal(8);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getConfiguredPool().getConnection();
    }



}
