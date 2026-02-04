package com.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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
    private HashSet<Nota> notas;

    private Stack<Observacao> observacoes;
}
