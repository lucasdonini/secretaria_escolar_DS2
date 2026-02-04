package com.controller;


import com.dao.AlunoDAO;
import com.dao.NotasDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(VisualizarNotasAlunoServlet.PATH)
public class VisualizarNotasAlunoServlet extends HttpServlet {

    public static final String PATH = "/aluno/visualizar-notas";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var matricula = UUID.fromString(req.getParameter("matricula"));

        try(var alunoDao = new AlunoDAO();
            var notaDao = new NotasDAO()
        ){
            var aluno  = alunoDao.buscarPorMatricula(matricula);
            notaDao.carregarNotas(aluno);

            //enviando jsp
            req.setAttribute("notasAluno", aluno.getNotas());
            req.getRequestDispatcher("visualizarNotasAluno.jsp");

        }catch (Throwable e){
            e.printStackTrace();
            req.getRequestDispatcher("erro.jsp");
        }

    }
}
