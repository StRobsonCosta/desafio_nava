package com.desafio.nava_log.application.service;

import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import com.desafio.nava_log.application.port.out.TransportadoraRepository;
import com.desafio.nava_log.domain.model.Transportadora;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportadoraService implements TransportadoraUseCase {

    private final TransportadoraRepository transportadoraRepository;

    @Override
    public Transportadora selecionarTransportadora(double peso) {
        List<Transportadora> transportadoras = transportadoraRepository.listarTransportadoras();

        return transportadoras.stream()
                .min(Comparator.comparing(t -> t.getTaxaPorKg().doubleValue()))
                .orElseThrow(() -> new RuntimeException("Nenhuma transportadora disponível"));
    }
}
