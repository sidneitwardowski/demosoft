package com.br.soft.demosoft.api.v1.mapper;

import com.br.soft.demosoft.api.v1.request.PautaRequest;
import com.br.soft.demosoft.api.v1.response.PautaResponse;
import com.br.soft.demosoft.model.Pauta;
import com.br.soft.demosoft.repository.PerguntaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PautaMapper {
    final PerguntaRepository perguntaRepository;
    final SessaoRepository sessaoRepository;

    public PautaResponse mapFromRequest(Pauta pauta) {
        var perguntas = perguntaRepository.findAllByPautaId(pauta.getId());

        var perguntasDto = perguntas.stream()
                .map(PerguntaMapper::mapResponse)
                .collect(Collectors.toList());

        return PautaResponse.builder()
                .dataReuniao(pauta.getDataReunicao())
                .descricao(pauta.getDescricao())
                .possuiSessao(sessaoRepository.existsByPautaId(pauta.getId()))
                .perguntas(perguntasDto)
                .build();
    }

    public PautaResponse mapToResponse(Pauta pauta) {
        var perguntas = perguntaRepository.findAllByPautaId(pauta.getId());

        var perguntasDto = perguntas.stream()
                .map(PerguntaMapper::map)
                .collect(Collectors.toList());

        return PautaResponse.builder()
                .id(pauta.getId())
                .dataReuniao(pauta.getDataReunicao())
                .descricao(pauta.getDescricao())
                .possuiSessao(sessaoRepository.existsByPautaId(pauta.getId()))
                .perguntas(perguntasDto)

                .build();
    }

    public Pauta mapFromRequest(PautaRequest dto) {
        return Pauta.builder()
                .dataReunicao(dto.getDataReuniao())
                .descricao(dto.getDescricao())
                .build();
    }
}
