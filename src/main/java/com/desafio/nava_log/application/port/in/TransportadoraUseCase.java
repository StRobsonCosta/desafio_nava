package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.domain.model.Transportadora;

public interface TransportadoraUseCase {
    Transportadora selecionarTransportadora(double peso);
}
