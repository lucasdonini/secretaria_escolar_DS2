<%@ page import="com.model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page import="com.utils.AtributoSessao" %>
<%@ page import="com.model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>

<%
    final var matricula = request.getParameter("matricula");
    var alunos = (List<Aluno>) session.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
    var professor = (Professor) session.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
    
    var aluno = alunos.stream().filter(a ->
        a.getMatricula().toString().equals(matricula)
    ).findFirst().orElseThrow();
    
    var materias = professor.getDisciplinas().stream().map(Enum::name).toList();
    var displayMaterias = materias.isEmpty() ? "Nenhuma matéria" : String.join(", ", materias);
%>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Mémora</title>
    <link rel="stylesheet" href="telaObservacoes.css">
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
                <p id="nome-professor">Prof. <%= professor.getNome() %> <br><%= displayMaterias %></p>
                <img src="${pageContext.request.contextPath}/assets/foto de perfil (antony).png" alt="foto de perfil do professor">
            </div>
        </div>
    </header>
    <main>
        <a href="${pageContext.request.contextPath}/pagina-professor/tela-inicial/tela-inicial.jsp">
            <div id="btn-voltar">
                <i class="bi bi-arrow-left-circle"></i>
                <p>Voltar</p>
            </div>
        </a>
        <div class="bloco-aluno">
            <div id="icone-user">
                <i class="bi bi-person"></i>
            </div>
            <div>
                <h2><%= aluno.getNome() %></h2>
                <p>Matrícula: <%= aluno.getMatricula() %></p>
            </div>
        </div>

        <div class="options">
            <a href="${pageContext.request.contextPath}/pagina-professor/detalhes-do-aluno/telaNotas.jsp">
                <div id="aba-notas">
                    <i class="bi bi-journal"></i>
                    <p>Notas</p>
                </div>
            </a>
            <div id="aba-observações">
                <i class="bi bi-chat-left-dots"></i>
                <p>Observações</p>
            </div>
        </div>

        <div id="pre-observacoes">
            <div style="display: flex; align-items: center; gap: 15px;">
                <h2>Observações</h2>
                <span id="contador-obs">0</span>
            </div>
            <button id="btn-adicionar-observacao">
                <i class="bi bi-plus"></i>
                <p>Adicionar Observação</p>
            </button>
        </div>

        <div id="box-nova-observacao" class="card-observacao">
            <h4>Nova observação</h4>
                <textarea id="texto-observacao" placeholder="Digite sua observação..."></textarea>

                <div class="botoes-comentario">
                    <button id="btn-cancelar">Cancelar</button>
                    <button id="btn-enviar">Enviar</button>
                </div>
        </div>

        <div id="container-observacoes"></div>
    </main>
    <script src="${pageContext.request.contextPath}/pagina-professor/detalhes-do-aluno/telaObservacoes.js"></script>
</body>

</html>