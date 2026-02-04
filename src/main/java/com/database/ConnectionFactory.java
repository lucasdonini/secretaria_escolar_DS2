package com.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        var url = dotenv.get("DB_URL");

        if (url == null) {
            throw new IllegalStateException("Environment variable 'DB_URL' not set");
        }

        return DriverManager.getConnection(url);
    }
}
