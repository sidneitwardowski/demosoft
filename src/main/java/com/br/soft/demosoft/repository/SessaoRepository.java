package com.br.soft.demosoft.repository;

import com.br.soft.demosoft.model.Sessao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessaoRepository extends MongoRepository<Sessao, String> {
    boolean existsByPautaId(String pautaId);
    Optional<Sessao> findByPautaId(String pautaId);
}
