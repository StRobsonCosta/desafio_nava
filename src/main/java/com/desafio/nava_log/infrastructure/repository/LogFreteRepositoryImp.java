package com.desafio.nava_log.infrastructure.repository;

import com.desafio.nava_log.application.port.out.LogFreteRepository;
import com.desafio.nava_log.domain.model.LogFrete;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogFreteRepositoryImp extends MongoRepository<LogFrete, UUID>, LogFreteRepository {
}
