package com.desafio.nava_log.application.service;

import com.desafio.nava_log.domain.model.Transportadora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FreteCalculator {
    private static final BigDecimal DIVISOR_DISTANCIA = BigDecimal.valueOf(10);

    public BigDecimal calcularFrete(Transportadora transportadora, Double peso, Double distancia) {
        if (Objects.isNull(transportadora) || Objects.isNull(peso) || peso <= 0 || Objects.isNull(distancia) || distancia < 0)
            throw new IllegalArgumentException("Parâmetros inválidos para o cálculo do frete.");


        BigDecimal taxaPorKg = transportadora.getTaxaPorKg();
        BigDecimal pesoBigDecimal = BigDecimal.valueOf(peso);
        BigDecimal distanciaBigDecimal = BigDecimal.valueOf(distancia);

        BigDecimal valorFrete = calcularValorBaseFrete(taxaPorKg, pesoBigDecimal);
        BigDecimal valorDistancia = calcularValorDistancia(distanciaBigDecimal);

        return valorFrete.add(valorDistancia);
    }

    private BigDecimal calcularValorBaseFrete(BigDecimal taxaPorKg, BigDecimal peso) {
        return taxaPorKg.multiply(peso);
    }

    private BigDecimal calcularValorDistancia(BigDecimal distancia) {
        return distancia.divide(DIVISOR_DISTANCIA, 2, RoundingMode.HALF_UP);
    }
}
