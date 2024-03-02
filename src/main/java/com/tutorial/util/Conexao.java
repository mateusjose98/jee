package com.tutorial.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final static String URL = "jdbc:mysql://localhost:3306/db_jee?serverTimezone=UTC";
    private final static String USER = "user";
    private final static String PASSWORD = "pass";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(URL, USER,PASSWORD);
        }
        return connection;
    }

}
