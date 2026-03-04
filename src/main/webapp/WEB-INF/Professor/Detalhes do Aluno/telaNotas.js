const btnAdicionar = document.getElementById("btn-adicionar-nota");
const boxNota = document.getElementById("box-adicionar-nota");
const btnCancelar = document.getElementById("cancelar-nota");
const btnSalvar = document.getElementById("salvar-nota")
const botoesEditar = document.querySelectorAll(".editar-nota");
const box = document.getElementById("box-editar-nota");
const titulo = document.getElementById("titulo-edicao");
const inputNota = document.getElementById("input-nota");
const selectNota = document.querySelector("#box-adicionar-nota select");
const inputNotaAtribuida = document.getElementById("nota-atribuida");

btnAdicionar.addEventListener("click", function(){
    boxNota.style.display = "block";
});

btnCancelar.addEventListener("click", function(){
    boxNota.style.display = "none";
});

let notaAtual = null;

btnSalvar.addEventListener("click", function(){
    const nota = inputNotaAtribuida.value;
    const tipoNota = selectNota.value; // "1ª Nota" ou "2ª Nota"
    
    if(nota && nota.trim() !== "") {
        // Encontra o card correspondente
        const cards = document.querySelectorAll(".nota-card");
        cards.forEach(card => {
            const pTitulo = card.querySelector("p:first-child");
            if(pTitulo.textContent === tipoNota) {
                const valorNota = card.querySelector(".valor-nota");
                valorNota.textContent = nota;
            }
        });
        
        // Fecha e limpa o box
        boxNota.style.display = "none";
        inputNotaAtribuida.value = "";
    } else {
        alert("Por favor, digite uma nota.");
    }
});

botoesEditar.forEach(botao => {

    botao.addEventListener("click", function(){

        const card = this.closest(".nota-card");

        notaAtual = card.querySelector(".valor-nota");

        const tipoNota = card.dataset.nota;

        titulo.innerText = "Editar " + tipoNota + "ª Nota";

        inputNota.value = notaAtual.innerText === "-" ? "" : notaAtual.innerText;

        box.style.display = "block";

    });

});

document.getElementById("salvar-edicao").addEventListener("click", function(){

    const novaNota = inputNota.value;

    if(novaNota !== ""){
        notaAtual.innerText = novaNota;
    }

    box.style.display = "none";

});

document.getElementById("cancelar-edicao").addEventListener("click", function(){

    box.style.display = "none";

});