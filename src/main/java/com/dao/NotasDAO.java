package com.dao;

import com.model.Aluno;
import com.model.Disciplina;
import com.model.Nota;

import java.sql.SQLException;
import java.util.UUID;

public class NotasDAO extends DAO {
    public NotasDAO() throws SQLException {
        super();
    }

    public void carregarNotas(Aluno aluno) throws SQLException {
        var sql = "SELECT n1, n2, cod_materia FROM notas WHERE matricula_aluno = ?";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, aluno.getMatricula());

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    var disciplina = Disciplina.deCodigo(rs.getInt("cod_materia"));
                    var nota = Nota.builder()
                            .n1(rs.getDouble("n1"))
                            .n2(rs.getDouble("n2"))
                            .disciplina(disciplina)
                            .build();

                    aluno.getNotas().add(nota);
                }
            }
        }
    }

    public void atualizarNota(UUID matriculaAluno, Nota nota) throws SQLException {
        var codDisciplina = nota.getDisciplina().getCodigo();
        var sql = "UPDATE notas SET n1 = ?, n2 = ? WHERE matricula_aluno = ? AND cod_materia = ?";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, nota.getN1());
            pstmt.setDouble(2, nota.getN2());
            pstmt.setObject(3, matriculaAluno);
            pstmt.setInt(4, codDisciplina);

            pstmt.executeUpdate();
        }
    }
}
