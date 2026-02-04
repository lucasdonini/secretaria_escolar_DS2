package com.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class Nota {
    @NonNull
    private Disciplina disciplina;
    private double n1;
    private double n2;

    public double media() {
        return (n1 + n2) / 2;
    }

    @Override
    public int hashCode() {
        return disciplina.hashCode();
    }
}
