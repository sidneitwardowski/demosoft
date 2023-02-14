package com.br.soft.demosoft.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResultadoDto {
    private String pergunta;
    private Long sim;
    private Long nao;
}
