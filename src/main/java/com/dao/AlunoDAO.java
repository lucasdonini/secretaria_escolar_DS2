package com.dao;

import com.model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlunoDAO extends DAO {
    public AlunoDAO() throws SQLException {
        super();
    }

    private static Aluno resultSetParaAluno(ResultSet rs) throws SQLException {
        return Aluno.builder()
                .nome(rs.getString("nome"))
                .matricula(rs.getObject("matricula", UUID.class))
                .usuario(rs.getString("usuario"))
                .email(rs.getString("email"))
                .senha(rs.getString("senha"))
                .build();
    }

    public Aluno buscarPorEmail(String email) throws SQLException {
        var sql = "SELECT nome, matricula, usuario, senha, email FROM aluno WHERE email = ?";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;
                return resultSetParaAluno(rs);
            }
        }
    }

    public Aluno buscarPorMatricula(UUID matricula) throws SQLException {
        var sql = "SELECT nome, matricula, usuario, senha, email FROM aluno WHERE matricula = ?";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, matricula);

            try (var rs = pstmt.executeQuery()) {
                if (!rs.next()) return null;
                return resultSetParaAluno(rs);
            }
        }
    }

    public void preCadastrar(String nome, UUID matricula, String usuario) throws SQLException {
        var sql = "INSERT INTO aluno(nome, matricula, usuario, email, senha) VALUES (?, ?, ?, NULL, NULL)";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setObject(2, matricula);
            pstmt.setString(3, usuario);
            pstmt.executeUpdate();
        }
    }

    public void completarCadastro(UUID matricula, String email, String senha) throws SQLException {
        var sql = "UPDATE aluno SET email = ?, senha = ? WHERE matricula = ?";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            pstmt.setObject(3, matricula);
            pstmt.executeUpdate();
        }
    }

    public List<Aluno> buscarPorProfessor(UUID idProfessor) throws SQLException {
        var sql = "SELECT DISTINCT a.matricula, a.nome, a.email, a.usuario, a.senha, a.email " +
                "FROM aluno a " +
                "JOIN notas n ON n.id_aluno = a.matricula " +
                "JOIN professor_disciplina pd ON pd.id_disciplina = n.id_disciplina " +
                "WHERE pd.id_professor = ? " +
                "ORDER BY a.nome";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, idProfessor);
            try (var rs = pstmt.executeQuery()) {
                var alunos = new ArrayList<Aluno>();
                while (rs.next()) alunos.add(resultSetParaAluno(rs));
                return alunos;
            }
        }
    }
}
