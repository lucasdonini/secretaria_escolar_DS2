function mostrarSenha() {
    var inputSenha = document.getElementById("senha")
    var botaoMostrarSenha = document.getElementById("botao-senha")

    if (inputSenha.type === 'password') {
        inputSenha.setAttribute('type', 'text')
        botaoMostrarSenha.classList.replace('bi-eye-slash', 'bi-eye')
    }
    else {
        inputSenha.setAttribute('type', 'password')
        botaoMostrarSenha.classList.replace('bi-eye', 'bi-eye-slash')
    }
}

function handleLogin() {
    // Navigate to the Professor initial screen
    window.location.href = '/src/main/webapp/WEB-INF/Professor/telaInicial.html';
}