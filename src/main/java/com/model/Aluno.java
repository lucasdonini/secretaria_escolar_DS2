package com.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Aluno {
    @NonNull
    private UUID matricula;

    @NonNull
    private String nome;

    @NonNull
    private String usuario;

    private String email;

    private String senha;

    @NonNull
    @Builder.Default
    private HashSet<Nota> notas = new HashSet<>();

    private Stack<Observacao> observacoes;

    public double mediaFinal() {
        return notas.stream().mapToDouble(Nota::media).average().orElse(0);
    }
}
