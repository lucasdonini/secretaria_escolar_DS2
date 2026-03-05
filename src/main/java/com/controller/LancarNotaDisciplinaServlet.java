package com.controller;

import com.dao.AlunoDAO;
import com.dao.NotasDAO;
import com.dao.ProfessorDAO;
import com.model.Aluno;
import com.model.Disciplina;
import com.model.Nota;
import com.utils.AtributoSessao;
import com.utils.PaginaJsp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(LancarNotaDisciplinaServlet.PATH)
public class LancarNotaDisciplinaServlet extends HttpServlet {

    public static final String PATH = "/professor/lancar-nota";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var sessao = req.getSession();
        var matricula = UUID.fromString(req.getParameter("matricula"));
        var usuario = req.getParameter("usuario");
        var n1 = Double.parseDouble(req.getParameter("n1"));
        var n2 = Double.parseDouble(req.getParameter("n2"));
        var codDisciplina = Integer.parseInt(req.getParameter("codigoDisciplina"));


        var disciplina = Disciplina.deCodigo(codDisciplina);
        var nota = Nota.builder().n1(n1).n2(n2).disciplina(disciplina).build();


        try (var notaDao = new NotasDAO(); var professorDao = new ProfessorDAO()) {
            var professor = professorDao.buscarPorUsuario(usuario);

            if (professor == null || !professor.getDisciplinas().contains(disciplina)) throw new RuntimeException();
            else notaDao.atualizarNota(matricula, nota);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var alunos = (List<Aluno>) sessao.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
        var aluno = alunos.stream().filter(a -> a.getMatricula().equals(matricula)).findFirst().orElseThrow();
        aluno.getNotas().add(nota);

        var destino = PaginaJsp.DETALHE_NOTAS_PROFESSOR + "?matricula=" + matricula;
        resp.sendRedirect(req.getContextPath() + destino);
    }
}
