package com.br.soft.demosoft.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@Builder
public class Sessao {
    @Id
    private String id;
    @DBRef
    private Pauta pauta;
    private LocalDateTime limite;
}
