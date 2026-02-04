package com.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Observacao(
        UUID id,
        UUID idRemetente,
        UUID idDestinatario,
        String mensagem,
        LocalDateTime dataEnvio
) {
}
