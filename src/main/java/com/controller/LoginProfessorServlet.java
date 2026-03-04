package com.controller;

import com.dao.AdminDAO;
import com.dao.AlunoDAO;
import com.dao.NotasDAO;
import com.dao.ProfessorDAO;
import com.model.Professor;
import com.utils.AtributoSessao;
import com.utils.NomeCookie;
import com.utils.PaginaJsp;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(LoginProfessorServlet.PATH)
public class LoginProfessorServlet extends HttpServlet {
    public static final String PATH = "/login/professor";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var sessao = req.getSession();
        var contextPath = req.getContextPath();
        var usuario = req.getParameter("usuario");
        var senha = req.getParameter("senha");
        Professor professor;

        try (var dao = new ProfessorDAO()) {
            professor = dao.buscarPorUsuario(usuario);
            boolean falhou;
            if (professor == null) {
                sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Email não encontrado");
                falhou = true;
            } else if (!Objects.equals(professor.getSenha(), senha)) {
                sessao.setAttribute(AtributoSessao.MENSAGEM_ERRO, "Email ou senha incorretos");
                falhou = true;
            } else {
                var cookie = new Cookie(NomeCookie.ID_PROFESSOR_LOGADO, professor.getId().toString());
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30 dias
                cookie.setPath("/");
                resp.addCookie(cookie);

                sessao.setAttribute(AtributoSessao.PROFESSOR_LOGADO, professor);
                falhou = false;
            }

            if (falhou) {
                resp.sendRedirect(contextPath + PaginaJsp.LOGIN);
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (var alunoDao = new AlunoDAO(); var notaDao = new NotasDAO()) {
            var alunos = alunoDao.buscarPorProfessor(professor.getId());
            for (var aluno : alunos) notaDao.carregarNotas(aluno);
            sessao.setAttribute(AtributoSessao.ALUNOS_PROFESSOR, alunos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + PaginaJsp.HOME_PROFESSOR);
    }
}
