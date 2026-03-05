const btnAdicionar = document.getElementById("btn-adicionar-nota");
const boxNota = document.getElementById("box-adicionar-nota");
const btnCancelar = document.getElementById("cancelar-nota");
const botoesEditar = document.querySelectorAll(".editar-nota");
const box = document.getElementById("box-editar-nota");
const titulo = document.getElementById("titulo-edicao");
const inputNota = document.getElementById("input-nota");

btnAdicionar.addEventListener("click", function(){
    boxNota.style.display = "block";
});

btnCancelar.addEventListener("click", function(){
    boxNota.style.display = "none";
});

let notaAtual = null;

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

document.getElementById("cancelar-edicao").addEventListener("click", function(){
    box.style.display = "none";
});