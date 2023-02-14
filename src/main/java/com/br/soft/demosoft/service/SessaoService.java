package com.br.soft.demosoft.service;

import com.br.soft.demosoft.model.Sessao;
import com.br.soft.demosoft.repository.PautaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoService {
    final PautaRepository pautaRepository;
    final SessaoRepository sessaoRepository;
    public void save(String pautaId, LocalDateTime limite){
        Sessao sessao =  null;
        var pauta = pautaRepository.findById(pautaId);

        if (pauta.isPresent()){
            sessao = Sessao.builder()
                    .pauta(pauta.get())
                    .limite(Objects.isNull(limite) ? LocalDateTime.now().plusMinutes(1) : limite)
                    .build();
        }else {
            throw new IllegalArgumentException("Pauta não encontrada");
        }

        if (isCadastrado(pautaId)){
            throw new RuntimeException("Sessão já criada");
        }
        sessaoRepository.save(sessao);
    }

    public List<Sessao> findAll(){
        return sessaoRepository.findAll();
    }

    private boolean isCadastrado(String pautaId){
        return sessaoRepository.existsByPautaId(pautaId);
    }

    public Optional<Sessao> findById(String id) {
        return sessaoRepository.findById(id);
    }

    public void delete(String sessaoId){
        sessaoRepository.deleteById(sessaoId);
    }
}
