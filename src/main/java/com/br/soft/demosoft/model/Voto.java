package com.br.soft.demosoft.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Voto {
    @Id
    private String id;
    private String cpf;
    @DBRef
    private Sessao sessao;

    private Pergunta pergunta;
    private VotoEnum voto;


}
