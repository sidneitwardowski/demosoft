package com.br.soft.demosoft.api.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PautaRequest {
    @Schema(description = "descrição da pauta")
    private String descricao;
    @Schema(description = "Data em que a reunião irá discutir a pauta")
    private LocalDate dataReuniao;
    @Schema(description = "Lista com as perguntas")
    private List<PerguntaRequest> perguntas;
}
