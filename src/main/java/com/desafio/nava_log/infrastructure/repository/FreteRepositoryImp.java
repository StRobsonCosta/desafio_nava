package com.desafio.nava_log.infrastructure.repository;

import com.desafio.nava_log.application.port.out.FreteRepository;
import com.desafio.nava_log.domain.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FreteRepositoryImp extends JpaRepository<Frete, UUID>, FreteRepository {
}
