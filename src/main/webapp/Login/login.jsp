<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="login.css">
    <title>Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>

<body>
    <div class="container">
        <div id="imagem">
            <img src="../assets/elefante roxo.png" alt="Elefante Roxo">
        </div>

        <div class="form-side">
            <div class="header-form">
                <a href="#" class="back-button">
                    <i class="bi bi-arrow-left-circle" id="voltar"></i>
                </a>
                <h1>Acesse sua conta</h1>
            </div>

            <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
                <div class="input">
                    <label for="username">Usuário</label>
                    <input type="text" id="username" placeholder="Digite seu nome de usuário ou e-mail" required>
                </div>

                <div class="input">
                    <label for="senha">Senha</label>
                    <div class="campo-senha">
                        <input type="password" id="senha" placeholder="Digite sua senha" required>
                        <i class="bi bi-eye-slash" id="botao-senha" onclick="mostrarSenha()"></i>
                    </div>
                </div>

                <button type="submit" class="btn-entrar" onclick="handleLogin()">Entrar</button>
                <!-- Fazer validação com JSP-->
            </form>

            <div class="footer-link">
                <p>Não tem acesso? <a href="../Cadastro/cadastro.html">Faça seu cadastro aqui.</a></p>
            </div>
        </div>
        <c:if test="${not empty requestScope.mensagemErro}">
            ${requestScope.mensagemErro}
        </c:if>
    </div>
    <script src="login.js"></script>
</body>

</html>
