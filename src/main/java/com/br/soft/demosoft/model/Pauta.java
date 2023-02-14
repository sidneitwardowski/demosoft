package com.br.soft.demosoft.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
@Builder
public class Pauta {
    @Id
    private String id;
    private String descricao;
    private LocalDate dataReunicao;

}
