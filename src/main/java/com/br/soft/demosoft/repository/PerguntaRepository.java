package com.br.soft.demosoft.repository;

import com.br.soft.demosoft.model.Pergunta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PerguntaRepository extends MongoRepository<Pergunta, String> {
    List<Pergunta> findAllByPautaId(String id);

    void deleteAllByPautaId(String id);
}
