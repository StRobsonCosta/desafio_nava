package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.domain.model.Transportadora;

import java.util.List;
import java.util.UUID;

public interface TransportadoraUseCase {
    Transportadora selecionarTransportadora(double peso);

    TransportadoraDto criarTransportadora(TransportadoraDto request);

    List<TransportadoraDto> listarTransportadoras();

    TransportadoraDto buscarTransportadora(UUID id);

    TransportadoraDto atualizarTransportadora(UUID id, TransportadoraDto request);

    void deletarTransportadora(UUID id);


}
