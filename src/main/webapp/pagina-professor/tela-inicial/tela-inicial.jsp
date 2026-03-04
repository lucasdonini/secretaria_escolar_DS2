<%@ page import="com.model.Professor" %>
<%@ page import="com.utils.AtributoSessao" %>
<%@ page import="com.model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>
<%
    var objSessao = session.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
    assert objSessao instanceof Professor;
    var professor = (Professor) objSessao;
    
    var materias = professor.getDisciplinas().stream().map(Enum::name).toList();
    var displayMaterias = materias.isEmpty() ? "Nenhuma matéria" : String.join(", ", materias);
%>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Mémoria</title>
    <link rel="stylesheet" href="telaInicial.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>

<body>
    <header>
        <div class="header">
            <img src="${pageContext.request.contextPath}/assets/logo.png" alt="logo Colégio Mémora" id="logo">
            <div id="professor">
                <p>Prof. <%= professor.getNome() %> <br><%= displayMaterias %></p>
                <img src="${pageContext.request.contextPath}/assets/foto de perfil (antony).png" alt="foto de perfil do professor">
            </div>
        </div>
    </header>
    <main>
        <a href="${pageContext.request.contextPath}/index.jsp">
            <div id="btn-voltar">
                <i class="bi bi-arrow-left-circle"></i>
                <p>Voltar</p>
            </div>
        </a>

        <div class="welcome-message">
            <h1>Bem-vindo, <%= professor.getNome() %>!</h1>
            <h3>Gerencie suas turmas</h3>
        </div>

        <div class="search-text">
            <h2>Buscar aluno</h2>
            <div id="search-field">
                <div class="input-search">
                    <input type="text" id="buscarMatricula" placeholder="Buscar por matrícula...">
                </div>
                <button id="botaoBuscar">Buscar</button>
            </div>
        </div>

        <table>
            <tr>
                <th>Matrícula</th>
                <th>Nome</th>
            </tr>
            <%
                var alunos = (List<Aluno>) session.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
                var i = 0;
                for (var aluno : alunos) {
            %>
            <tr class="<%= (i % 2 == 0) ? "fundo-escuro" : ""%>">
                <td><%= aluno.getMatricula() %></td>
                <td><a href="${pageContext.request.contextPath}/pagina-professor/detalhes-do-aluno/telaNotas.html"><%= aluno.getNome() %></a></td>
            </tr>
            <% i++; } %>
        </table>
    </main>
</body>

</html>