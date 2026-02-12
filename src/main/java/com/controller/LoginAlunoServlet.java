package com.controller;

import com.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(LoginAlunoServlet.PATH)
public class LoginAlunoServlet extends HttpServlet {
    public static final String PATH = "/login/aluno";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var email = req.getParameter("usuario");
        var senha = req.getParameter("senha");

        try (var dao = new AlunoDAO()) {
            var aluno = dao.buscarPorEmail(email);
            if (aluno == null) return; // TODO: resposta negativa

            if (!Objects.equals(aluno.getSenha(), senha)) return; // TODO: resposta negativa

            // TODO: resposta positiva
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            req.setAttribute("mensagemErro", "O Login não foi efetuado");
            req.getRequestDispatcher("erro.jsp").forward(req, resp);
        }
    }
}
