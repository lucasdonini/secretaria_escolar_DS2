package com.controller;

import com.utils.AtributoSessao;
import com.utils.PaginaJsp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CapturadorDeExcecoes extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            var session = req.getSession();
            session.setAttribute(AtributoSessao.MENSAGEM_ERRO, e.getMessage());
            resp.sendRedirect(req.getContextPath() + PaginaJsp.ERRO);
        }
    }
}
