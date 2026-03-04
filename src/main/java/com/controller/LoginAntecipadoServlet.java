package com.controller;

import com.dao.AlunoDAO;
import com.dao.ProfessorDAO;
import com.model.Aluno;
import com.model.Professor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebFilter("/pagina-login/*")
public class LoginAntecipadoServlet extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        var cookies = req.getCookies();
        var contextPath = req.getContextPath();
        var sessao = req.getSession();

        var professorSessao = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        if (professorSessao instanceof Professor) {
            resp.sendRedirect(contextPath + PaginasJsp.HOME_PROFESSOR);
            return;
        }

        var alunoSessao = sessao.getAttribute(AtributoSessao.ALUNO_LOGADO);
        if (alunoSessao instanceof Aluno) {
            resp.sendRedirect(contextPath + PaginasJsp.HOME_ALUNO);
            return;
        }

        var usuarioProfessor = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NomeCookie.USUARIO_PROFESSOR_LOGADO))
                .map(Cookie::getValue)
                .findFirst();

        if (usuarioProfessor.isPresent()) {
            Professor professor;
            try (var dao = new ProfessorDAO()) {
                professor = dao.buscarPorUsuario(usuarioProfessor.get());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (professor == null) chain.doFilter(req, resp);

            sessao.setAttribute(AtributoSessao.PROFESSOR_LOGADO, professor);
            resp.sendRedirect(contextPath + PaginasJsp.HOME_PROFESSOR);
            return;
        }

        var emailAlunoLogado = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(NomeCookie.EMAIL_ALUNO_LOGADO))
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
            resp.sendRedirect(contextPath + PaginasJsp.HOME_ALUNO);
            return;
        }

        chain.doFilter(req, resp);
    }
}
