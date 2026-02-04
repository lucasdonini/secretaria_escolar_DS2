package com.dao;

import com.model.Aluno;
import com.model.Disciplina;
import com.model.Nota;
import com.model.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotasDAO extends DAO {
    public NotasDAO() throws SQLException {
        super();
    }

    private static Nota resultSetParaNota(ResultSet rs) throws SQLException {
        var disciplina = Disciplina.deCodigo(rs.getInt("cod_materia"));
        return Nota.builder()
                .n1(rs.getDouble("n1"))
                .n2(rs.getDouble("n2"))
                .disciplina(disciplina)
                .build();
    }

    public void carregarNotas(Aluno aluno) throws SQLException {
        var sql = "SELECT n1, n2, cod_materia FROM notas WHERE matricula_aluno = ?";
        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, aluno.getMatricula());

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    var nota = resultSetParaNota(rs);
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


    public List<Nota> buscarNotasPorProfessor(Professor professor) throws SQLException {
        var sql = """
                SELECT n.n1, n.n2, n.cod_materia
                FROM nota n
                JOIN disciplina d ON d.codigo = n.cod_materia
                WHERE n.cod_materia = ?
                """;

        var notas = new ArrayList<Nota>();
        try (var pstmt = conn.prepareStatement(sql)) {
            for (var disciplina : professor.getDisciplinas()) {
                var codigo = disciplina.getCodigo();
                pstmt.setInt(1, codigo);

                try (var rs = pstmt.executeQuery()) {
                    while (rs.next()) notas.add(resultSetParaNota(rs));
                }
            }

            return notas.stream().distinct().toList();
        }
    }
}
