package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(LoginServlet.PATH)
public class LoginServlet extends HttpServlet {
    public static final String PATH = "/login";
    private static final String LOGIN_PROFESSOR = LoginProfessorServlet.PATH;
    private static final String LOGIN_ALUNO = LoginAlunoServlet.PATH;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var usuario = req.getParameter("usuario");
        req.getRequestDispatcher(usuario.contains("@") ? LOGIN_ALUNO : LOGIN_PROFESSOR).forward(req, resp);
    }
}
