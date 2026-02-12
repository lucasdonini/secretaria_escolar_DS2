package com.controller;

import com.dao.AlunoDAO;
import com.dao.ObservacaoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(VisualizarObservacoesAlunosServlet.PATH)
public class VisualizarObservacoesAlunosServlet extends HttpServlet {

    public static final String PATH = "/aluno/visualizar-observacoes";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var matricula = UUID.fromString(req.getParameter("matricula"));

        try(var alunoDao = new AlunoDAO();
            var observacoesDao = new ObservacaoDAO()
        ){
            var aluno = alunoDao.buscarPorMatricula(matricula);

            observacoesDao.carregarObservacoes(aluno);

            //mandando as observações para o jsp
            req.setAttribute("observacoes", aluno.getObservacoes());
            req.getRequestDispatcher("visualizarObservacoesAluno.jsp").forward(req, resp);

        }catch (Throwable e){
            e.printStackTrace(System.err);
            req.setAttribute("mensagemErro", "As observações não foram encontradas!");
            req.getRequestDispatcher("erro.jsp").forward(req, resp);
        }
    }
}
