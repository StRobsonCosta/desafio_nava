package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.domain.model.Frete;

import java.util.List;
import java.util.UUID;

public interface FreteUseCase {
    FreteDto calcularFrete(String cepOrigem, String cepDestino, Double peso);

    FreteDto buscarFretePeloId(UUID id);

    List<FreteDto> buscarFretePorCeps(String cepOrigem, String cepDestino);

    List<FreteDto> buscarTodosFretes();

}
