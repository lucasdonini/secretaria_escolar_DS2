package com.controller;

import com.dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(BuscarAlunoMatriculaServlet.PATH)
public class BuscarAlunoMatriculaServlet extends HttpServlet {

    public static final String PATH = "/app/buscar-aluno-por-matricula";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var matricula = UUID.fromString(req.getParameter("matricula"));

        try(var alunoDao = new AlunoDAO()){
            var aluno = alunoDao.buscarPorMatricula(matricula);

            //ENVIANDO PARA O JSP
            req.setAttribute("aluno", aluno);
            req.getRequestDispatcher("buscarPorMatricula.jsp").forward(req, resp);

        }catch (Throwable e){
            e.printStackTrace(System.err);
            req.setAttribute("mensagemErro", "Aluno não encontrado");
            req.getRequestDispatcher("erroBuscarAlunoMatricula.jsp").forward(req, resp);
        }

    }
}
