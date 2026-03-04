package com.dao;

import com.model.Administrador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AdminDAO extends DAO {
    public AdminDAO() throws SQLException {
        super();
    }

    private static Administrador resultSetParaAdmin(ResultSet rs) throws SQLException {
        return Administrador.builder()
                .id(rs.getObject("id", UUID.class))
                .email(rs.getString("email"))
                .senha(rs.getString("senha"))
                .build();
    }

    public Administrador buscarPorId(UUID id) throws SQLException {
        try (var pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE id = ?")) {
            pstmt.setObject(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;
                return resultSetParaAdmin(rs);
            }
        }
    }

    public Administrador buscarPorEmail(String email) throws SQLException {
        try (var pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE email = ?")) {
            pstmt.setString(1, email);
            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;
                return resultSetParaAdmin(rs);
            }
        }
    }
}
