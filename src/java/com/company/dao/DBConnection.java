package com.company.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/inventory",
                        "postgres", // your DB username
                        "CEPRNDPM" // your DB password
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
