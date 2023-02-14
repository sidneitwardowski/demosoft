package com.br.soft.demosoft.Stub;

import com.br.soft.demosoft.model.Pauta;
import com.br.soft.demosoft.model.Pergunta;

public class PerguntaStub {
    public static Pergunta getPergunta(Pauta pauta) {
        return Pergunta.builder()
                .pautaId(pauta.getId())
                .descricao("Pergunta stub")
                .build();
    }
}
