package com.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Disciplina {
    MATEMATICA(0),
    PORTUGUES(1),
    HISTORIA(2),
    CIENCIAS(3),
    INFORMATICA(4);

    private final int codigo;

    Disciplina(int codigo) {
        this.codigo = codigo;
    }

    public static Disciplina deCodigo(final int codigo) {
        var materia = Arrays.stream(values()).filter(d -> d.codigo == codigo).findFirst();
        if (materia.isEmpty())
            throw new IllegalArgumentException("Não existe nenhuma matéria com esse código: %d".formatted(codigo));

        return materia.get();
    }
}
