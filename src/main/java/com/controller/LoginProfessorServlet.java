package com.controller;

import com.dao.AlunoDAO;
import com.dao.ProfessorDAO;
import jakarta.servlet.ServletException;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var usuario = req.getParameter("usuario");
        var senha = req.getParameter("senha");

        try (var dao = new ProfessorDAO()) {
            var professor = dao.buscarPorUsuario(usuario);
            if (professor == null) return; // TODO: resposta negativa

            if (!Objects.equals(professor.getSenha(), senha)) return; // TODO: resposta negativa

            // TODO: resposta positiva
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            // TODO: resposta negativa
        }
    }
}
