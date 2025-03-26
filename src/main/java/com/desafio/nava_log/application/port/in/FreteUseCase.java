package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.domain.model.Frete;

public interface FreteUseCase {
    Frete calcularFrete(String cepOrigem, String cepDestino, Double peso);
}
