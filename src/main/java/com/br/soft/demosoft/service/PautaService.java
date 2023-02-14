package com.br.soft.demosoft.service;

import com.br.soft.demosoft.model.Pauta;
import com.br.soft.demosoft.model.Pergunta;
import com.br.soft.demosoft.repository.PautaRepository;
import com.br.soft.demosoft.repository.PerguntaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PautaService {
    final PautaRepository pautaRepository;
    final PerguntaRepository perguntaRepository;
    final SessaoRepository sessaoRepository;

    public void save(Pauta pauta, List<Pergunta> perguntas) {
        log.info("PautaService -> cadastrando a pauta {}, com as perguntas {}", pauta, perguntas);
        var pautaCadastrata = pautaRepository.save(pauta);
        perguntas.forEach(pergunta -> pergunta.setPautaId(pautaCadastrata.getId()));
        var perguntaCadastrada = perguntaRepository.saveAll(perguntas);
        var pautaAtualizada = pautaRepository.save(pauta);
        log.info("pauta cadastrada com sucesso. {}", pautaAtualizada);
        log.info("perguntas cadastradas com sucesso. {}", perguntaCadastrada);

    }

    public Pauta update(Pauta pauta, List<Pergunta> perguntas) throws IllegalArgumentException {
        log.info("PautaService -> atualizando a pauta {}, com as perguntas {}", pauta, perguntas);

        if(sessaoRepository.existsByPautaId(pauta.getId())){
            throw new RuntimeException("Não é possivel atualizar uma pauta quando já existe uma sessão de votação criada");
        }

        var pautaCadastrata = pautaRepository.findById(pauta.getId());
        if (pautaCadastrata.isEmpty()) {
            throw new IllegalArgumentException("Pauta não localizada.");
        }
        BeanUtils.copyProperties(pautaCadastrata, pauta, "id", "perguntas");
        perguntas.forEach(pergunta -> pergunta.setPautaId(pautaCadastrata.get().getId()));
        perguntaRepository.deleteAllByPautaId(pautaCadastrata.get().getId());
        var perguntaCadastrada = perguntaRepository.saveAll(perguntas);
        var pautaAtualizada = pautaRepository.save(pauta);
        log.info("pauta atualizada com sucesso. {}", pautaAtualizada);
        log.info("perguntas cadastradas com sucesso. {}", perguntaCadastrada);
        return pautaAtualizada;
    }

    public List<Pauta> findAll() {
        log.info("Obtendo lista de todas as pautas");
        return pautaRepository.findAll();
    }

    public Optional<Pauta> findById(String id) {
        log.info("Obtendo pauta através do id {}", id);
        return pautaRepository.findById(id);
    }

    public void delete(String id) {
        log.info("deletando a pauta com id {}", id);

        if(sessaoRepository.existsByPautaId(id)){
            throw new RuntimeException("Não é possivel deletar uma pauta quando já existe uma sessão de votação criada");
        }

        var pauta = pautaRepository.findById(id);
        pauta.ifPresent(pautas -> {
                    perguntaRepository.deleteAllByPautaId(pauta.get().getId());
                    pautaRepository.deleteById(pauta.get().getId());

                }
        );
    }
}
