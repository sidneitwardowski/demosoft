package com.br.soft.demosoft.api.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoRequest {
    @Schema(description = "Id da pauta")
    private String pautaId;
    @Schema(description = "Data limite para a votação")
    private LocalDateTime limite;
}
