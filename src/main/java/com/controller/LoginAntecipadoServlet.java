package com.controller;

import com.dao.AlunoDAO;
import com.dao.NotasDAO;
import com.dao.ProfessorDAO;
import com.model.Aluno;
import com.model.Professor;
import com.utils.AtributoSessao;
import com.utils.NomeCookie;
import com.utils.PaginaJsp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

@WebFilter("/pagina-login/*")
public class LoginAntecipadoServlet extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        var cookies = req.getCookies();
        var contextPath = req.getContextPath();
        var sessao = req.getSession();

        var professorSessao = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        if (professorSessao instanceof Professor) {
            resp.sendRedirect(contextPath + PaginaJsp.HOME_PROFESSOR);
            return;
        }

        var alunoSessao = sessao.getAttribute(AtributoSessao.ALUNO_LOGADO);
        if (alunoSessao instanceof Aluno) {
            resp.sendRedirect(contextPath + PaginaJsp.HOME_ALUNO);
            return;
        }

        var idProfessor = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NomeCookie.ID_PROFESSOR_LOGADO))
                .map(Cookie::getValue)
                .findFirst();

        if (idProfessor.isPresent()) {
            Professor professor;
            var id = UUID.fromString(idProfessor.get());
            try (var dao = new ProfessorDAO()) {
                professor = dao.buscarPorId(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (professor == null) {
                chain.doFilter(req, resp);
                return;
            }

            try (var alunoDao = new AlunoDAO(); var notaDao = new NotasDAO()) {
                var alunos = alunoDao.buscarPorProfessor(professor.getId());
                for (var aluno : alunos) notaDao.carregarNotas(aluno);
                sessao.setAttribute(AtributoSessao.ALUNOS_PROFESSOR, alunos);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            sessao.setAttribute(AtributoSessao.PROFESSOR_LOGADO, professor);
            resp.sendRedirect(contextPath + PaginaJsp.HOME_PROFESSOR);
            return;
        }

        var emailAlunoLogado = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NomeCookie.ID_ALUNO_LOGADO))
                .map(Cookie::getValue)
                .findFirst();

        if (emailAlunoLogado.isPresent()) {
            Aluno aluno;
            try (var dao = new AlunoDAO()) {
                aluno = dao.buscarPorEmail(emailAlunoLogado.get());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            sessao.setAttribute(AtributoSessao.ALUNO_LOGADO, aluno);
            resp.sendRedirect(contextPath + PaginaJsp.HOME_ALUNO);
            return;
        }

        chain.doFilter(req, resp);
    }
}
