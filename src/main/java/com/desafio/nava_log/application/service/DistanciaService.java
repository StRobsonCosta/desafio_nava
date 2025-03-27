package com.desafio.nava_log.application.service;

import com.desafio.nava_log.adapter.dto.CoordenadaDto;
import com.desafio.nava_log.application.port.in.CepCoordenadasUseCase;
import com.desafio.nava_log.application.port.in.DistanciaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DistanciaService implements DistanciaUseCase {

    private static final double RAIO_TERRA_KM = 6371.0;
    private final CepCoordenadasUseCase cepCoordenadasUseCase;

    @Override
    public Double calcularDistanciaPorCep(String cepOrigem, String cepDestino) {
        CoordenadaDto origem = cepCoordenadasUseCase.obterCoordenadas(cepOrigem);
        CoordenadaDto destino = cepCoordenadasUseCase.obterCoordenadas(cepDestino);

        if (Objects.isNull(origem) || Objects.isNull(destino))
            throw new IllegalArgumentException("Coordenadas dos CEPs não encontradas.");

        return calcularDistancia(origem, destino);
    }

    @Override
    public Double calcularDistanciaGeografica(String cepOrigem, String cepDestino, String cepTransportadora) {
        CoordenadaDto coordenadaOrigem = cepCoordenadasUseCase.obterCoordenadas(cepOrigem);
        CoordenadaDto coordenadaDestino = cepCoordenadasUseCase.obterCoordenadas(cepDestino);
        CoordenadaDto coordenadaTransportadora = cepCoordenadasUseCase.obterCoordenadas(cepTransportadora);

        if (Objects.isNull(coordenadaOrigem) || Objects.isNull(coordenadaDestino) || Objects.isNull(coordenadaTransportadora))
            throw new IllegalArgumentException("Não foi possível obter coordenadas para um dos CEPs informados.");

        double distanciaOrigemTransportadora = calcularDistancia(coordenadaOrigem, coordenadaTransportadora);
        double distanciaDestinoTransportadora = calcularDistancia(coordenadaDestino, coordenadaTransportadora);

        return (distanciaOrigemTransportadora + distanciaDestinoTransportadora) / 2.0;
    }

    private Double calcularDistancia(CoordenadaDto c1, CoordenadaDto c2) {

        double lat1 = Math.toRadians(c1.getLatitude());
        double lon1 = Math.toRadians(c1.getLongitude());
        double lat2 = Math.toRadians(c2.getLatitude());
        double lon2 = Math.toRadians(c2.getLongitude());

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_KM * c;
    }

}
