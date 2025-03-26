package com.desafio.nava_log.application.port.out;

import com.desafio.nava_log.adapter.dto.EnderecoDto;

public interface CepApiClient {
    EnderecoDto consultarCep(String cep);
}
