package com.br.soft.demosoft.api.v1.mapper;

import com.br.soft.demosoft.api.v1.request.VotoRequestList;
import com.br.soft.demosoft.model.Voto;
import com.br.soft.demosoft.repository.PautaRepository;
import com.br.soft.demosoft.repository.PerguntaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VotoMapper {
    final PautaRepository pautaRepository;
    final SessaoRepository sessaoRepository;
    final PerguntaRepository perguntaRepository;

    public Voto map(VotoRequestList request, String pautaId, String perguntaId, String cpf) {
        return Voto.builder()
                .voto(request.getVoto())
                .pergunta(perguntaRepository.findById(perguntaId).get())
                .cpf(cpf)
                .sessao(sessaoRepository.findByPautaId(pautaId).get())
                .build();

    }


}
