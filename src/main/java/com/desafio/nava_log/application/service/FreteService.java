package com.desafio.nava_log.application.service;

import com.desafio.nava_log.application.port.in.FreteUseCase;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import com.desafio.nava_log.domain.model.Frete;
import com.desafio.nava_log.domain.model.Transportadora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FreteService implements FreteUseCase {

    private final TransportadoraUseCase transportadoraUseCase;

    @Override
    public Frete calcularFrete(String cepOrigem, String cepDestino, Double peso) {
        Transportadora transportadora = transportadoraUseCase.selecionarTransportadora(peso);
        BigDecimal valorFrete = transportadora.getTaxaPorKg().multiply(BigDecimal.valueOf(peso));

        return new Frete(null, cepOrigem, cepDestino, BigDecimal.valueOf(peso), valorFrete, transportadora);
    }
}
