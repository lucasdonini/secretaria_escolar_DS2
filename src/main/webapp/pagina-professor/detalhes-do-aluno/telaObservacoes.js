const btnAdicionar = document.getElementById('btn-adicionar-observacao');
const container = document.getElementById('container-observacoes');
const contador = document.getElementById('contador-obs');
const nomeProfessor = document.getElementById('nome-professor').innerText;

// Elementos da Box de Input
const boxInput = document.getElementById('box-nova-observacao');
const campoTexto = document.getElementById('texto-observacao');
const btnEnviar = document.getElementById('btn-enviar');
const btnCancelar = document.getElementById('btn-cancelar');

let totalObservacoes = 0;

// 1. Mostrar a box ao clicar em "Adicionar Observação"
btnAdicionar.addEventListener('click', () => {
    boxInput.style.display = 'block';
    campoTexto.focus(); // Coloca o cursor na caixa de texto
});

// 2. Esconder e limpar ao clicar em "Cancelar"
btnCancelar.addEventListener('click', () => {
    fecharLimparBox();
});

// 3. Adicionar o card ao clicar em "Enviar"
btnEnviar.addEventListener('click', () => {
    const texto = campoTexto.value;

    if (texto && texto.trim() !== "") {
        adicionarCard(texto);
        fecharLimparBox();
    } else {
        alert("Por favor, digite uma observação.");
    }
});

function fecharLimparBox() {
    boxInput.style.display = 'none';
    campoTexto.value = "";
}

function adicionarCard(mensagem) {
    totalObservacoes++;
    contador.innerText = totalObservacoes;

    const agora = new Date();
    const dataFormatada = agora.toLocaleDateString('pt-BR') + ' às ' + agora.toLocaleTimeString('pt-BR', {hour: '2-digit', minute:'2-digit'});

    const novoCard = document.createElement('div');
    novoCard.classList.add('card-observacao');

    novoCard.innerHTML = `
        <h4 style="font-weight: bold;">${nomeProfessor}</h4>
        <p>${mensagem}</p>
        <div class="data-obs">${dataFormatada}</div>
    `;

    container.prepend(novoCard);
}