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

@WebFilter(urlPatterns = {"/professor/*", "/pagina-professor/*"})
public class FiltroAutenticacaoProfessor extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var sessao = req.getSession();
        var professor = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        if (professor instanceof Professor) {
            chain.doFilter(req, resp);
            return;
        }

        sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Você precisa estar logado");
        resp.sendRedirect(req.getContextPath() + PaginaJsp.LOGIN);
    }
}
