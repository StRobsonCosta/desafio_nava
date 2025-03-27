package com.desafio.nava_log.service;

import com.desafio.nava_log.adapter.dto.CoordenadaDto;
import com.desafio.nava_log.application.port.in.CepCoordenadasUseCase;
import com.desafio.nava_log.application.service.DistanciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistanciaServiceTest {

    private static final String CEP_SP = "01001-000";
    private static final String CEP_RJ = "20040-020";
    private static final String CEP_BH = "30130-010";
    private static final String CEP_INVALIDO = "00000-000";

    @Mock
    private CepCoordenadasUseCase cepCoordenadasUseCase;

    @InjectMocks
    private DistanciaService distanciaService;

    @Test
    void deveCalcularDistanciaPorCepComSucesso() {
        String cepOrigem = CEP_SP;
        String cepDestino = CEP_RJ;

        CoordenadaDto origem = new CoordenadaDto(-23.55052, -46.633308); // ÉssePê
        CoordenadaDto destino = new CoordenadaDto(-22.906847, -43.172897); // ÉrreJota

        when(cepCoordenadasUseCase.obterCoordenadas(cepOrigem)).thenReturn(origem);
        when(cepCoordenadasUseCase.obterCoordenadas(cepDestino)).thenReturn(destino);

        Double distancia = distanciaService.calcularDistanciaPorCep(cepOrigem, cepDestino);

        assertNotNull(distancia);
        assertTrue(distancia > 0);
    }

    @Test
    void deveLancarExcecaoQuandoCepOrigemOuDestinoForInvalido() {
        when(cepCoordenadasUseCase.obterCoordenadas(CEP_INVALIDO)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                distanciaService.calcularDistanciaPorCep(CEP_INVALIDO, CEP_RJ));
    }

    @Test
    void deveCalcularDistanciaGeograficaComSucesso() {
        String cepOrigem = CEP_SP;
        String cepDestino = CEP_RJ;
        String cepTransportadora = CEP_BH;

        CoordenadaDto origem = new CoordenadaDto(-23.55052, -46.633308); // ÉssePê
        CoordenadaDto destino = new CoordenadaDto(-22.906847, -43.172897); // ÉrreJota
        CoordenadaDto transportadora = new CoordenadaDto(-19.93053, -43.93525); // BêAgá

        when(cepCoordenadasUseCase.obterCoordenadas(cepOrigem)).thenReturn(origem);
        when(cepCoordenadasUseCase.obterCoordenadas(cepDestino)).thenReturn(destino);
        when(cepCoordenadasUseCase.obterCoordenadas(cepTransportadora)).thenReturn(transportadora);

        Double distancia = distanciaService.calcularDistanciaGeografica(cepOrigem, cepDestino, cepTransportadora);

        assertNotNull(distancia);
        assertTrue(distancia > 0);
    }

    @Test
    void deveLancarExcecaoQuandoCepTransportadoraForInvalido() {
        lenient().when(cepCoordenadasUseCase.obterCoordenadas(anyString())).thenReturn(new CoordenadaDto(-23.55052, -46.633308));
        when(cepCoordenadasUseCase.obterCoordenadas(CEP_BH)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                distanciaService.calcularDistanciaGeografica(CEP_SP, CEP_RJ, CEP_BH));
    }

}
