package com.controller;

import com.model.Professor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/professor/*")
public class FiltroAutenticacao extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var sessao = req.getSession();
        var professor = sessao.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
        if (professor instanceof Professor) chain.doFilter(req, resp);

        sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Você precisa estar logado");
        resp.sendRedirect(req.getContextPath() + PaginasJsp.LOGIN);
    }
}
