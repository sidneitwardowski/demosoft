package com.br.soft.demosoft.api.v1.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PautaResponse {
    @Schema(description = "Id da pauta cadastrada")
    private String id;
    @Schema(description = "descrição da pauta")
    private String descricao;
    @Schema(description = "Data em que a reunião irá discutir a pauta")
    private LocalDate dataReuniao;
    @Schema(description = "Lista com as perguntas")
    private List<PerguntaResponse> perguntas;
    @Schema(description = "informa se possiu uma sessão criada ")
    private boolean possuiSessao;

}
