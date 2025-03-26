package com.desafio.nava_log.application.service;

import com.desafio.nava_log.application.port.out.CepApiClient;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CepValidationService {

    private final CepApiClient cepApiClient;

    public void validarCep(String cepOrigem, String cepDestino) {
        if (Objects.isNull(cepApiClient.consultarCep(cepOrigem))  || Objects.isNull(cepApiClient.consultarCep(cepDestino))) {
            throw new CepNaoEncontradoException("CEP de origem ou destino inválido.");
        }
    }
}
