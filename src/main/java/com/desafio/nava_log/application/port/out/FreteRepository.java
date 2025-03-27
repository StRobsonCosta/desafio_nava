package com.desafio.nava_log.application.port.out;

import com.desafio.nava_log.domain.model.Frete;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FreteRepository {

    Frete save(Frete frete);

    Optional<Frete> findById(UUID id);

    List<Frete> findByCepOrigemAndCepDestino(String Origem, String destino);

    List<Frete> findAll();

}
