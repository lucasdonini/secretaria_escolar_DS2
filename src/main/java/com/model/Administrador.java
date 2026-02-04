package com.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class Administrador {
    @NonNull
    private UUID id;

    @NonNull
    private String email;

    @NonNull
    private String senha;
}
