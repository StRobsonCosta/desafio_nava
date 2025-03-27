package com.desafio.nava_log.application.service;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.application.port.in.FreteUseCase;
import com.desafio.nava_log.application.port.in.LogFreteUseCase;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import com.desafio.nava_log.application.port.out.FreteRepository;
import com.desafio.nava_log.domain.exception.TransportadoraNaoEncontradaException;
import com.desafio.nava_log.domain.model.Frete;
import com.desafio.nava_log.domain.model.Transportadora;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreteService implements FreteUseCase {

    private final CepValidationService cepValidationService;
    private final TransportadoraUseCase transportadoraUseCase;
    private final FreteRepository freteRepository;
    private final FreteCalculator freteCalculator;
    private final DistanciaService distanciaService;

    @Override
    public FreteDto calcularFrete(String cepOrigem, String cepDestino, Double peso) {
        log.info("Iniciando cálculo de frete: origem={}, destino={}, peso={}kg", cepOrigem, cepDestino, peso);

        if (Objects.isNull(peso) || peso <= 0)
            throw new IllegalArgumentException("O peso do frete deve ser maior que zero.");

        cepValidationService.validarCep(cepOrigem, cepDestino);

        double distanciaKm = distanciaService.calcularDistanciaPorCep(cepOrigem, cepDestino);

        Transportadora transportadora = transportadoraUseCase.selecionarTransportadora(peso, cepOrigem, cepDestino);
        if (Objects.isNull(transportadora))
            throw new TransportadoraNaoEncontradaException("Nenhuma transportadora disponível para o peso informado.");

        BigDecimal valorFrete = freteCalculator.calcularFrete(transportadora, peso, distanciaKm);

        final Frete frete = getFrete(cepOrigem, cepDestino, peso, valorFrete, transportadora);

        freteRepository.save(frete);
        log.info("Frete salvo no banco: {}", frete);

        String mensagem = String.format(
                "O frete do CEP %s para o CEP %s é de R$ %.2f",
                cepOrigem, cepDestino, valorFrete
        );

        return new FreteDto(frete, mensagem);
    }

    private static Frete getFrete(String cepOrigem, String cepDestino, Double peso, BigDecimal valorFrete, Transportadora transportadora) {
        return Frete.builder()
                .cepOrigem(cepOrigem)
                .cepDestino(cepDestino)
                .peso(BigDecimal.valueOf(peso))
                .valor(valorFrete)
                .transportadora(transportadora)
                .build();
    }
}
