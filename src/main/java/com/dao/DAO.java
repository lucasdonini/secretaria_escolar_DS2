package com.dao;

import com.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO implements AutoCloseable {
    protected Connection conn;

    protected DAO() throws SQLException {
        conn = ConnectionFactory.getConnection();
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
