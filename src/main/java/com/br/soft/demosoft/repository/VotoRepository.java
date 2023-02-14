package com.br.soft.demosoft.repository;

import com.br.soft.demosoft.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface VotoRepository extends MongoRepository<Voto, String> {
    boolean existsByCpfAndSessaoIdAndPerguntaId(String cpf, String sessaoId, String perguntaId);
    List<Voto> findAllBySessaoId(String sessaoId);

}
