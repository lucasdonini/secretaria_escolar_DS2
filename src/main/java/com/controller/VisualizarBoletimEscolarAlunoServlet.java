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

@WebServlet(VisualizarBoletimEscolarAlunoServlet.PATH)
public class VisualizarBoletimEscolarAlunoServlet extends HttpServlet {

    public static final String PATH = "/aluno/visualizar-boletim";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var matricula = UUID.fromString(req.getParameter("matricula"));

        try(var alunoDao = new AlunoDAO();
            var notasDao = new NotasDAO()
        ){
            var aluno = alunoDao.buscarPorMatricula(matricula);

            notasDao.carregarNotas(aluno);

            req.setAttribute("notas", aluno.getNotas());
            req.setAttribute("mediaFinal", aluno.mediaFinal());

            //enviando jsp
            req.getRequestDispatcher("visualizarBoletimAluno.jsp");

        }catch (Throwable e){
            e.printStackTrace(System.err);
            req.getRequestDispatcher("erro.jsp");
        }


    }
}
