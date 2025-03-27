package com.desafio.nava_log.application.service;

import com.desafio.nava_log.adapter.dto.CoordenadaDto;
import com.desafio.nava_log.application.port.in.CepCoordenadasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CepCoordenadasService implements CepCoordenadasUseCase {

    private static final Map<String, CoordenadaDto> cepsCoordenadas = Map.ofEntries(
            Map.entry("01001-000", new CoordenadaDto(-23.55052, -46.633308)), // São Paulo
            Map.entry("20040-020", new CoordenadaDto(-22.906847, -43.172897)), // Rio de Janeiro
            Map.entry("30130-010", new CoordenadaDto(-19.93053, -43.93525)), // Belo Horizonte
            Map.entry("40020-010", new CoordenadaDto(-12.9714, -38.5014)), // Salvador
            Map.entry("60025-000", new CoordenadaDto(-3.71722, -38.5433)), // Fortaleza
            Map.entry("80010-000", new CoordenadaDto(-25.4284, -49.2733)), // Curitiba
            Map.entry("90010-150", new CoordenadaDto(-30.0331, -51.2300)), // Porto Alegre
            Map.entry("69005-000", new CoordenadaDto(-3.11903, -60.2441)), // Manaus
            Map.entry("70710-907", new CoordenadaDto(-15.7801, -47.9292)), // Brasília
            Map.entry("64000-080", new CoordenadaDto(-5.091, -42.803)), // Teresina
            Map.entry("08753-360", new CoordenadaDto(-23.5239, -46.1889)) // Mogi das Cruzes
    );

    @Override
    public CoordenadaDto obterCoordenadas(String cep) {
        return cepsCoordenadas.getOrDefault(cep, null);
    }
}
