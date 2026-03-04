package com.controller;

import com.model.Aluno;
import com.utils.AtributoSessao;
import com.utils.PaginaJsp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/aluno/*", "/pagina-aluno/*"})
public class FiltroAutenticacaoAluno extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var sessao = req.getSession();
        var aluno = sessao.getAttribute(AtributoSessao.ALUNO_LOGADO);
        if (aluno instanceof Aluno) chain.doFilter(req, resp);

        sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Você precisa estar logado");
        resp.sendRedirect(req.getContextPath() + PaginaJsp.LOGIN);
    }
}
