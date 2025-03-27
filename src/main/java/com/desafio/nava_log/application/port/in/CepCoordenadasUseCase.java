package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.adapter.dto.CoordenadaDto;

public interface CepCoordenadasUseCase {
    CoordenadaDto obterCoordenadas(String cep);
}
