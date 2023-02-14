package com.br.soft.demosoft.api.v1.mapper;

import com.br.soft.demosoft.api.v1.request.PerguntaRequest;
import com.br.soft.demosoft.api.v1.response.PerguntaResponse;
import com.br.soft.demosoft.model.Pergunta;

public class PerguntaMapper {
    public static PerguntaResponse map(Pergunta pergunta) {
        return PerguntaResponse.builder()
                .id(pergunta.getId())
                .descricao(pergunta.getDescricao())
                .build();
    }

    public static Pergunta map(PerguntaRequest dto) {
        return Pergunta.builder()
                .descricao(dto.getDescricao())
                .build();
    }

    public static PerguntaResponse mapResponse(Pergunta dto) {
        return PerguntaResponse.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .build();
    }
}
