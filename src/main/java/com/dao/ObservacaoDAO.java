package com.dao;

import com.model.Aluno;
import com.model.Observacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;
import java.util.UUID;

public class ObservacaoDAO extends DAO {
    public ObservacaoDAO() throws SQLException {
        super();
    }

    private static Observacao resultSetParaObservacao(ResultSet rs) throws SQLException {
        return Observacao.builder()
                .idRemetente(rs.getObject("id_remetente", UUID.class))
                .mensagem(rs.getString("mensagem"))
                .build();
    }

    public void carregarObservacoes(Aluno aluno) throws SQLException {
        var sql = """
                SELECT o.id_remetente, o.mensagem, o.data_envio FROM observacao o
                JOIN aluno a ON a.matricula = o.id_destinatario
                WHERE a.matricula = ?
                ORDER BY o.data_envio
                """;

        var observacoes = aluno.getObservacoes();
        observacoes.clear();

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, aluno.getMatricula());

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    var observacao = resultSetParaObservacao(rs);
                    observacoes.push(observacao);
                }
            }
        }
    }

    public void registrarObservacao(Observacao observacao) throws SQLException {
        var sql = """
                INSERT INTO observacoes(id_remetente, id_destinatario, mensagem, data_envio) 
                VALUES (?, ?, ?, ?)
                """;

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, observacao.idRemetente());
            pstmt.setObject(2, observacao.idDestinatario());
            pstmt.setString(3, observacao.mensagem());
            pstmt.setObject(4, observacao.dataEnvio());
            pstmt.executeUpdate();
        }
    }

    public Stack<Observacao> buscarPorRemetente(UUID idRementente) throws SQLException {
        var stk = new Stack<Observacao>();
        var sql = """
                SELECT id_destinatario, mensagem, data_envio FROM observacoes
                WHERE id_remetente = ?
                ORDER BY data_envio
                """;

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, idRementente);

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    var observacao = resultSetParaObservacao(rs);
                    stk.push(observacao);
                }
            }
        }

        return stk;
    }
}
