package com.dao;

import com.model.Disciplina;
import com.model.Professor;

import java.sql.SQLException;

public class ProfessorDAO extends DAO {
    public ProfessorDAO() throws SQLException {
        super();
    }

    public Professor buscarPorUsuario(String usuario) throws SQLException {
        var sql = "SELECT senha, nome, disciplina FROM professor WHERE usuario = ?";

        try(var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);

            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;

                var professor = Professor.builder()
                        .senha(rs.getString("senha"))
                        .nome(rs.getString("nome"))
                        .build();

                var disciplina = Disciplina.deCodigo(rs.getInt("disciplina"));
                professor.adicionarDisciplina(disciplina);

                return professor;
            }
        }
    }
}
