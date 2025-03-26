package com.desafio.nava_log.infrastructure.repository;

import com.desafio.nava_log.application.port.out.TransportadoraRepository;
import com.desafio.nava_log.domain.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportadoraRepositoryImp extends JpaRepository<Transportadora, UUID>, TransportadoraRepository {
}
