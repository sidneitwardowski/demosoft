package com.br.soft.demosoft.api.v1.mapper;


import com.br.soft.demosoft.api.v1.response.SessaoResponse;
import com.br.soft.demosoft.model.Sessao;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SessaoMapper {
    public static SessaoResponse map(Sessao sessao) {

        return SessaoResponse.builder()
                .descricao(sessao.getPauta().getDescricao())
                .limite(sessao.getLimite())
                .status(sessao.getLimite().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .build();
    }
}
