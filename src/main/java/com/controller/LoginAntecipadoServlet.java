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
import java.util.List;
import java.util.UUID;

@WebFilter("/pagina-login/*")
public class LoginAntecipadoServlet extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        var cookies = req.getCookies();
        var contextPath = req.getContextPath();
        var sessao = req.getSession();
        var destinoFinal = sessao.getAttribute(AtributoSessao.DESTINO_FINAL);

        var professorSessao = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        var alunosProfessor = sessao.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
        if (professorSessao instanceof Professor && alunosProfessor instanceof List) {
            var destino = destinoFinal == null ? PaginaJsp.HOME_PROFESSOR : destinoFinal;
            resp.sendRedirect(contextPath + destino);
            return;
        }

        var alunoSessao = sessao.getAttribute(AtributoSessao.ALUNO_LOGADO);
        if (alunoSessao instanceof Aluno) {
            var destino = destinoFinal == null ? PaginaJsp.HOME_ALUNO : destinoFinal;
            resp.sendRedirect(contextPath + destino);
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

            var destino = destinoFinal == null ? PaginaJsp.HOME_PROFESSOR : destinoFinal;
            sessao.setAttribute(AtributoSessao.PROFESSOR_LOGADO, professor);
            resp.sendRedirect(contextPath + destino);
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

            var destino = destinoFinal == null ? PaginaJsp.HOME_ALUNO : destinoFinal;
            sessao.setAttribute(AtributoSessao.ALUNO_LOGADO, aluno);
            resp.sendRedirect(contextPath + destino);
            return;
        }

        chain.doFilter(req, resp);
    }
}
