package com.br.soft.demosoft.repository;

import com.br.soft.demosoft.model.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepository extends MongoRepository<Pauta, String> {
}
