const observacoes = [];

function adicionarObservacao(professor, disciplina, texto, data) {
    const novaObs = {
        professor,
        disciplina,
        texto,
        data
    };

    observacoes.push(novaObs);

    renderizarObservacoes();
}

function renderizarObservacoes() {
    const container = document.getElementById("container-observacoes");
    const contador = document.getElementById("contador-obs");

    container.innerHTML = ""; // limpa antes de recriar

    observacoes.forEach(obs => {
        const card = document.createElement("div");
        card.classList.add("card-observacao");

        card.innerHTML = `
            <h4>Prof. ${obs.professor} - ${obs.disciplina}</h4>
            <p>${obs.texto}</p>
            <small>${obs.data}</small>
        `;

        container.appendChild(card);
    });

    contador.textContent = observacoes.length;
}