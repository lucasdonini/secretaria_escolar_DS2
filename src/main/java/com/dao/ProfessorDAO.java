package com.dao;

import com.model.Disciplina;
import com.model.Professor;

import java.sql.SQLException;
import java.util.UUID;

public class ProfessorDAO extends DAO {
    public ProfessorDAO() throws SQLException {
        super();
    }

    public Professor buscarPorId(UUID id) throws SQLException {
        var sql = """
                SELECT p.usuario, p.senha, p.nome, pd.id_disciplina
                FROM professor p
                LEFT JOIN professor_disciplina pd ON p.id = pd.id_professor
                WHERE p.id = ?
                """;

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);

            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;

                var professor = Professor.builder()
                        .id(id)
                        .usuario(rs.getString("usuario"))
                        .senha(rs.getString("senha"))
                        .nome(rs.getString("nome"))
                        .build();

                do {
                    var codDisciplina = rs.getObject("id_disciplina", Integer.class);
                    if (codDisciplina != null) {
                        var disciplina = Disciplina.deCodigo(codDisciplina);
                        professor.adicionarDisciplina(disciplina);
                    }
                } while (rs.next());

                return professor;
            }
        }
    }

    public Professor buscarPorUsuario(String usuario) throws SQLException {
        var sql = """
                SELECT p.id, p.senha, p.nome, pd.id_disciplina
                FROM professor p
                LEFT JOIN professor_disciplina pd ON p.id = pd.id_professor
                WHERE p.usuario = ?
                """;

        try(var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);

            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;

                var professor = Professor.builder()
                        .id(rs.getObject("id", UUID.class))
                        .usuario(usuario)
                        .senha(rs.getString("senha"))
                        .nome(rs.getString("nome"))
                        .build();

                do {
                    var codDisciplina = rs.getObject("id_disciplina", Integer.class);
                    if (codDisciplina != null) {
                        var disciplina = Disciplina.deCodigo(codDisciplina);
                        professor.adicionarDisciplina(disciplina);
                    }
                } while (rs.next());

                return professor;
            }
        }
    }
}
