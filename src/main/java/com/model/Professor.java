package com.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Professor {
    @NonNull
    private UUID id;

    @NonNull
    @Builder.Default
    private HashSet<Disciplina> disciplinas = new HashSet<>();

    @NonNull
    private String nome;

    @NonNull
    private String usuario;

    @NonNull
    private String senha;

    public void adicionarDisciplinas(Collection<Disciplina> disciplinas) {
        this.disciplinas.addAll(disciplinas);
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public void removerDisciplina(Disciplina disciplina){
        disciplinas.remove(disciplina);
    }
}
