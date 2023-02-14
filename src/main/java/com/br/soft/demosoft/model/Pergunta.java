package com.br.soft.demosoft.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Pergunta {
    @Id
    private String id;

    private String pautaId;

    private String descricao;



}
