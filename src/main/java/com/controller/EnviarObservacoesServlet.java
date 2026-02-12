package com.controller;

import com.dao.ObservacaoDAO;
import com.model.Observacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet(EnviarObservacoesServlet.PATH)
public class EnviarObservacoesServlet extends HttpServlet {

    public static final String PATH = "/professor/enviar-observacoes";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var id = UUID.randomUUID();
        var idRemetente = UUID.fromString(req.getParameter("idremetente"));
        var idDestinatario = UUID.fromString(req.getParameter("idDestinatario"));
        var mensagem = req.getParameter("mensagem");
        var dataEnvio = LocalDateTime.now();

        var observacao = Observacao.builder().id(id).idRemetente(idRemetente).idDestinatario(idDestinatario).mensagem(mensagem).dataEnvio(dataEnvio).build();

        try(var observacaoDao = new ObservacaoDAO()){

            observacaoDao.registrarObservacao(observacao);

        }catch (Throwable e){
            e.printStackTrace(System.err);
            req.setAttribute("mensagemErro", "Observação não lançada!");
            req.getRequestDispatcher("erro.jsp").forward(req, resp);
        }

    }
}
