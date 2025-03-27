package com.desafio.nava_log.service;

import com.desafio.nava_log.application.service.FreteCalculator;
import com.desafio.nava_log.domain.model.Transportadora;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreteCalculatorTest {

    @Mock
    private Transportadora transportadora;

    @InjectMocks
    private FreteCalculator freteCalculator;

    @Test
    void deveCalcularFreteComSucesso() {
        when(transportadora.getTaxaPorKg()).thenReturn(BigDecimal.valueOf(5.0));

        double peso = 20.0;
        double distancia = 100.0;

        BigDecimal resultado = freteCalculator.calcularFrete(transportadora, peso, distancia);

        BigDecimal esperado = BigDecimal.valueOf(110.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(esperado, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoTransportadoraForNula() {
        assertThrows(IllegalArgumentException.class, () ->
                freteCalculator.calcularFrete(null, 10.0, 50.0));
    }

    @Test
    void deveLancarExcecaoQuandoPesoForInvalido() {

        assertThrows(IllegalArgumentException.class, () ->
                freteCalculator.calcularFrete(transportadora, 0.0, 50.0));

        assertThrows(IllegalArgumentException.class, () ->
                freteCalculator.calcularFrete(transportadora, -5.0, 50.0));
    }

    @Test
    void deveLancarExcecaoQuandoDistanciaForNegativa() {

        assertThrows(IllegalArgumentException.class, () ->
                freteCalculator.calcularFrete(transportadora, 10.0, -20.0));
    }
}
