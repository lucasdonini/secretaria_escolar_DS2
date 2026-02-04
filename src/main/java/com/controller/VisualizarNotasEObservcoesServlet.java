package com.controller;

import com.dao.AlunoDAO;
import com.dao.NotasDAO;
import com.dao.ObservacaoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(VisualizarNotasEObservcoesServlet.PATH)
public class VisualizarNotasEObservcoesServlet extends HttpServlet {

    public static final String PATH = "/aluno/visualizar-notas-e-observacoes";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var matricula = UUID.fromString(req.getParameter("matricula"));

        try(var alunoDao = new AlunoDAO();
            var notasDao = new NotasDAO();
            var observacaoDao = new ObservacaoDAO();
        ){

            var aluno = alunoDao.buscarPorMatricula(matricula);

            if (aluno != null){
                notasDao.carregarNotas(aluno);
                observacaoDao.carregarObservacoes(aluno);

                //SETANDO OS ATRIBUTOS PARA O JSP E MANDANDO ELES
                req.setAttribute("notas", aluno.getNotas());
                req.setAttribute("observacoes", aluno.getObservacoes());

                req.getRequestDispatcher("visualizarNotasEObservacoes.jsp").forward(req, resp);
            }
            else{
                throw new NullPointerException();
            }
        }catch (Throwable e){
            e.printStackTrace();
            req.setAttribute("mensagemErro", "Erro ao tentar abrir as notas e observações");
            req.getRequestDispatcher("erroVisualizarNotasEObservacoes.jsp").forward(req, resp);
        }
    }
}
