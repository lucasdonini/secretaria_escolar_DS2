package com.controller;

import com.model.Professor;
import com.utils.AtributoSessao;
import com.utils.PaginaJsp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/professor/*", "/pagina-professor/*"})
public class FiltroAutenticacaoProfessor extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var sessao = req.getSession();
        var professor = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        var alunos = sessao.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
        if (professor instanceof Professor && alunos instanceof List) {
            chain.doFilter(req, resp);
            return;
        }

        sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Você precisa estar logado");
        resp.sendRedirect(req.getContextPath() + PaginaJsp.LOGIN);
    }
}
