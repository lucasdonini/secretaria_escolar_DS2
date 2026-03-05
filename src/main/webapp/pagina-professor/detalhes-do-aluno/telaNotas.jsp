<%@ page import="com.utils.AtributoSessao" %>
<%@ page import="com.model.Professor" %>
<%@ page import="com.model.Aluno" %>
<%@ page import="java.util.List" %>
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
    <link rel="stylesheet" href="telaNotas.css">
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
            <div id="aba-notas">
                <i class="bi bi-journal"></i>
                <p>Notas</p>
            </div>
            <a href="${pageContext.request.contextPath}/pagina-professor/detalhes-do-aluno/telaObservacoes.jsp">
                <div id="aba-observações">
                    <i class="bi bi-chat-left-dots"></i>
                    <p>Observações</p>
                </div>
            </a>
        </div>

        <div id="pre-notas">
            <h2>Notas</h2>
            <button id="btn-adicionar-nota">
                <i class="bi bi-plus"></i>
                Adicionar Nota
            </button>
        </div>

        <form id="box-adicionar-nota" method="post" action="${pageContext.request.contextPath}/professor/lancar-nota">
            <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
            <input type="hidden" name="usuario" value="<%= professor.getUsuario() %>">
            
            
            <h3>Lançamento de Nota</h3>

            <div class="inputs-nota">
                <input name="n1" type="number" step=".01" min="0" max="10" placeholder="Digite a n1">
                <input name="n2" type="number" step=".01" min="0" max="10" placeholder="Digite a n2">
                
                <select name="codigoDisciplina">
                    <option value="" selected>-- Selecione --</option>
                    <% for (var disciplina : professor.getDisciplinas()) { %>
                    <option value="<%= disciplina.getCodigo() %>"><%=disciplina%></option>
                    <% } %>
                </select>
            </div>

            <div class="botoes-nota">
                <button id="cancelar-nota">Cancelar</button>
                <button id="salvar-nota" type="submit">Concluir</button>
            </div>
        </form>

        <div id="box-editar-nota" style="display:none">

            <h3 id="titulo-edicao"></h3>

            <div id="bloco-digitar-nota">
                <input type="number" id="input-nota" placeholder="Digite a nova nota">

                <div class="botoes">
                    <button id="cancelar-edicao">Cancelar</button>
                    <button id="salvar-edicao">Salvar</button>
                </div>
            </div>

        </div>

        <div class="notas-container">
            <% var i = 1; for (var nota : aluno.getNotas()) { %>
            <div class="nota-card" data-nota="1">
                <p><%= i %>ª Nota - <%= nota.getDisciplina() %></p>
                <div class="edit-grade-and-grade">
                    <i class="bi bi-pencil-square editar-nota"></i>
                    <p class="valor-nota">N1: <%= nota.getN1() %></p>
                    <p class="valor-nota">N2: <%= nota.getN2() %></p>
                    <p class="valor-nota">Media: <%= nota.media() %></p>
                </div>
            </div>
            <% i++; } %>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/pagina-professor/detalhes-do-aluno/telaNotas.js"></script>
</body>

</html>