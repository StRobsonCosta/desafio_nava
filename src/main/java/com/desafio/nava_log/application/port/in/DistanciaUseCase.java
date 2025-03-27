package com.desafio.nava_log.application.port.in;

public interface DistanciaUseCase {
    Double calcularDistanciaPorCep(String cepOrigem, String cepDestino);
    Double calcularDistanciaGeografica(String cepOrigem, String cepDestino, String cepTransportadora);
}
