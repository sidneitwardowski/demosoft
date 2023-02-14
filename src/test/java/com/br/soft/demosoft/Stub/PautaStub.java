package com.br.soft.demosoft.Stub;

import com.br.soft.demosoft.model.Pauta;

import java.time.LocalDate;
import java.util.UUID;

public class PautaStub {
    public static Pauta getPauta(){
        return Pauta.builder()
                .id(UUID.randomUUID().toString())
                .descricao("Pauta stub")
                .dataReunicao(LocalDate.now())
                .build();
    }
}
