package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.domain.model.Frete;

public interface FreteUseCase {
    FreteDto calcularFrete(String cepOrigem, String cepDestino, Double peso);
}
