package com.desafio.nava_log.application.port.out;

import com.desafio.nava_log.domain.model.Transportadora;

import java.util.List;
import java.util.Optional;

public interface TransportadoraRepository {
    Optional<Transportadora> buscarPorCep(String cep);
    List<Transportadora> listarTransportadoras();
}
