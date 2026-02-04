package com.controller;

import com.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

import java.io.IOException;

@WebServlet(PreCadastroAlunoServlet.PATH)
public class PreCadastroAlunoServlet extends HttpServlet{

    public static final String PATH = "/login/pre-cadastro-do-aluno";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var nome = req.getParameter("nome");
        var matricula = UUID.fromString(req.getParameter("matricula"));
        var email = req.getParameter("email");

        try (var dao = new AlunoDAO()) {
             dao.preCadastrar(nome, matricula, email);

        } catch (Throwable e) {
            e.printStackTrace(System.err);
            req.setAttribute("mensagemErro", "Aluno não foi cadastrado");
            req.getRequestDispatcher("erroPreCadastroAluno.jsp").forward(req, resp);
        }

    }
}
