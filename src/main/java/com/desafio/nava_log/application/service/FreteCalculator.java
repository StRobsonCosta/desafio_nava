package com.desafio.nava_log.application.service;

import com.desafio.nava_log.domain.model.Transportadora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FreteCalculator {
    public BigDecimal calcularFrete(Transportadora transportadora, Double peso) {
        return transportadora.getTaxaPorKg().multiply(BigDecimal.valueOf(peso));
    }
}
