package com.br.soft.demosoft.api.v1.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessaoResponse {
    private String descricao;
    private LocalDateTime limite;
    private boolean status;
}
