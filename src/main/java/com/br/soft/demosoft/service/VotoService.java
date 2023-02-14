package com.br.soft.demosoft.service;

import com.br.soft.demosoft.api.v1.client.PessoaClient;
import com.br.soft.demosoft.model.Voto;
import com.br.soft.demosoft.model.VotoEnum;
import com.br.soft.demosoft.model.dto.ResultadoDto;
import com.br.soft.demosoft.repository.PerguntaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import com.br.soft.demosoft.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotoService {
    final VotoRepository votoRepository;
    final SessaoRepository sessaoRepository;
    final PessoaClient client;
    final PerguntaRepository perguntaRepository;

    public void votar(List<Voto> votos) {
        var sessao = sessaoRepository.findById(votos.get(0).getSessao().getId());

        sessao.ifPresent(param -> {

            if (!podeVotar(votos.get(0).getCpf())) {
                throw new RuntimeException("Nao foi possivel validar o cpf");
            }

            if (!votacaoDentroDoHorario(param.getLimite())) {
                throw new RuntimeException("Não é possível votar, votação encerrada");
            }

            votos.forEach(voto -> {

                if (votoRepository.existsByCpfAndSessaoIdAndPerguntaId(voto.getCpf(), voto.getSessao().getId(), voto.getPergunta().getId())) {
                    throw new RuntimeException("Nao é possivel votar duas vezes");
                }

                votoRepository.save(voto);

            });

        });


    }

    private boolean podeVotar(String cpf) {
        return client.verificaCpf(cpf)
                .get()
                .getStatus()
                .equals("ABLE_TO_VOTE");
    }

    public boolean votacaoDentroDoHorario(LocalDateTime data) {
        return LocalDateTime.now().isBefore(data);
    }

    public List<ResultadoDto> resultado(String sessaoid, String pautaId) {
        var perguntas = perguntaRepository.findAllByPautaId(pautaId);

        var lista = votoRepository.findAllBySessaoId(sessaoid);

        var votoSim = lista.stream()
                .filter(filtro -> filtro.getVoto().equals(VotoEnum.SIM))
                .collect(Collectors.groupingBy(
                        voto -> voto.getPergunta().getDescricao(),
                        Collectors.counting()
                ));

        var votoNao = lista.stream()
                .filter(filtro -> filtro.getVoto().equals(VotoEnum.NAO))
                .collect(Collectors.groupingBy(
                        voto -> voto.getPergunta().getDescricao(),
                        Collectors.counting()
                ));

        Set<ResultadoDto> resultado = new HashSet<>();

        lista.forEach(item -> {
            var resultadoDto = ResultadoDto.builder()
                    .pergunta(item.getPergunta().getDescricao())
                    .sim(Objects.isNull(votoSim.get(item.getPergunta().getDescricao())) ? 0L : votoSim.get(item.getPergunta().getDescricao()))
                    .nao(Objects.isNull(votoNao.get(item.getPergunta().getDescricao())) ? 0L : votoNao.get(item.getPergunta().getDescricao()))
                    .build();
            resultado.add(resultadoDto);
        });

        perguntas.forEach(pergunta -> {
            if (!(votoSim.containsKey(pergunta.getDescricao()) || votoNao.containsKey(pergunta.getDescricao()))) {
                var voto = ResultadoDto.builder()
                        .pergunta(pergunta.getDescricao())
                        .sim(0L)
                        .nao(0L)
                        .build();

                resultado.add(voto);

            }
        });

        return new ArrayList<>(resultado);
    }

}
