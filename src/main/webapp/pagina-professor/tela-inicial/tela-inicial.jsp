<%@ page import="com.model.Professor" %>
<%@ page import="com.utils.AtributoSessao" %>
<%@ page import="com.model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Nota" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>
<%
    var professor = (Professor) session.getAttribute(AtributoSessao.PROFESSOR_LOGADO);
    var alunos = (List<Aluno>) session.getAttribute(AtributoSessao.ALUNOS_PROFESSOR);
    
    var materias = professor.getDisciplinas().stream().map(Enum::name).toList();
    var displayMaterias = materias.isEmpty() ? "Nenhuma matéria" : String.join(", ", materias);
%>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Colégio Mémora</title>
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
        
        <div class="insights-container">
            
            <div class="insight-card">
                <div class="icone">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256">
                        <rect width="256" height="256" fill="none" />
                        <line x1="32" y1="64" x2="32" y2="144" fill="none" stroke="currentColor" stroke-linecap="round"
                              stroke-linejoin="round" stroke-width="16" />
                        <path d="M56,216c15.7-24.08,41.11-40,72-40s56.3,15.92,72,40" fill="none" stroke="currentColor"
                              stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                        <polygon points="224 64 128 96 32 64 128 32 224 64" fill="none" stroke="currentColor"
                                 stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                        <path d="M169.34,82.22a56,56,0,1,1-82.68,0" fill="none" stroke="currentColor"
                              stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                    </svg>
                </div>
                <div class="insight-content">
                    <h2><%= alunos.size() %></h2>
                    <p>Total de alunos</p>
                </div>
            </div>
            
            <div class="insight-card">
                <div class="icone">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256">
                        <rect width="256" height="256" fill="none" />
                        <path d="M32,216V56a8,8,0,0,1,8-8H216a8,8,0,0,1,8,8V216l-32-16-32,16-32-16L96,216,64,200Z"
                              fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                              stroke-width="16" />
                        <polyline points="64 160 96 96 128 160" fill="none" stroke="currentColor" stroke-linecap="round"
                                  stroke-linejoin="round" stroke-width="16" />
                        <line x1="72" y1="144" x2="120" y2="144" fill="none" stroke="currentColor"
                              stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                        <line x1="144" y1="128" x2="192" y2="128" fill="none" stroke="currentColor"
                              stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                        <line x1="168" y1="104" x2="168" y2="152" fill="none" stroke="currentColor"
                              stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                    </svg>
                </div>
                <div class="insight-content">
                    <h2><%=
                      alunos.stream()
                          .mapToInt(a -> a.getNotas().size())
                          .sum()
                    %></h2>
                    <p>Notas lançadas</p>
                </div>
            </div>
            <div class="insight-card">
                <div class="icone">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256">
                        <rect width="256" height="256" fill="none" />
                        <polyline points="224 208 32 208 32 48" fill="none" stroke="currentColor" stroke-linecap="round"
                                  stroke-linejoin="round" stroke-width="16" />
                        <polyline points="200 72 128 144 96 112 32 176" fill="none" stroke="currentColor"
                                  stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                        <polyline points="200 112 200 72 160 72" fill="none" stroke="currentColor"
                                  stroke-linecap="round" stroke-linejoin="round" stroke-width="16" />
                    </svg>
                </div>
                <div class="insight-content">
                    <h2><%=
                    Math.round(alunos.stream()
                        .flatMapToDouble(a ->
                            a.getNotas()
                                .stream()
                                .mapToDouble(Nota::media)
                        ).average()
                        .orElse(0) * 100) / 100.0
                    %></h2>
                    <p>Média geral dos alunos</p>
                </div>
            </div>
        
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
                var i = 0;
                for (var aluno : alunos) {
            %>
            <tr
                class="<%= (i % 2 == 0) ? "fundo-escuro" : ""%>"
                onclick="gotoDetails('${pageContext.request.contextPath}', '<%= aluno.getMatricula() %>')">
                <td><%= aluno.getMatricula() %></td>
                <td><%= aluno.getNome() %></td>
            </tr>
            <% i++; } %>
        </table>
    </main>
    <script src="tela-inicial.js"></script>
</body>

</html>