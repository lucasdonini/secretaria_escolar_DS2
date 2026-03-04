package com.controller;

import com.dao.ProfessorDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(LoginProfessorServlet.PATH)
public class LoginProfessorServlet extends HttpServlet {
    public static final String PATH = "/login/professor";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var session = req.getSession();
        var path = req.getContextPath();
        var usuario = req.getParameter("usuario");
        var senha = req.getParameter("senha");

        try (var dao = new ProfessorDAO()) {
            var professor = dao.buscarPorUsuario(usuario);
            var falhou = false;
            if (professor == null) {
                session.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Email não encontrado");
                falhou = true;
            } else if (!Objects.equals(professor.getSenha(), senha)) {
                session.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Email ou senha incorretos");
                falhou = true;
            }

            session.setAttribute(AtributoSessao.PROFESSOR_LOGADO, !falhou);
            resp.sendRedirect(path + (falhou ? PaginasJsp.LOGIN : PaginasJsp.HOME_PROFESSOR));
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            session.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Erro interno");
            resp.sendRedirect(path + PaginasJsp.LOGIN);
        }
    }
}
