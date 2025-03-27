package com.desafio.nava_log.service;

import com.desafio.nava_log.adapter.dto.CoordenadaDto;
import com.desafio.nava_log.application.service.CepCoordenadasService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CepCoordenadasServiceTest {

    private CepCoordenadasService cepCoordenadasService;

    @BeforeEach
    void setUp() {
        cepCoordenadasService = new CepCoordenadasService();
    }

    @Test
    void deveRetornarCoordenadasQuandoCepForValido() {
        String cepValido = "01001-000";

        CoordenadaDto coordenada = cepCoordenadasService.obterCoordenadas(cepValido);

        assertNotNull(coordenada);
        assertEquals(-23.55052, coordenada.getLatitude());
        assertEquals(-46.633308, coordenada.getLongitude());
    }

    @Test
    void deveRetornarNullQuandoCepForInvalido() {
        String cepInvalido = "99999-999";

        CoordenadaDto coordenada = cepCoordenadasService.obterCoordenadas(cepInvalido);

        assertNull(coordenada);
    }
}
