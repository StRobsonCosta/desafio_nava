package com.desafio.nava_log.application.service;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import com.desafio.nava_log.application.port.out.TransportadoraRepository;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import com.desafio.nava_log.domain.model.Transportadora;
import com.desafio.nava_log.infrastructure.repository.TransportadoraRepositoryImp;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransportadoraService implements TransportadoraUseCase {

//    private final TransportadoraRepository transportadoraRepository;

    private final TransportadoraRepositoryImp transportadoraRepository;

    @Override
    public Transportadora selecionarTransportadora(double peso) {
        List<Transportadora> transportadoras = transportadoraRepository.findAll();

        return transportadoras.stream()
                .min(Comparator.comparing(t -> t.getTaxaPorKg().doubleValue()))
                .orElseThrow(() -> new RuntimeException("Nenhuma transportadora disponível"));
    }

    @Transactional
    @Override
    public TransportadoraDto criarTransportadora(TransportadoraDto request) {
        log.info("Iniciando a criação de uma nova transportadora: {}", request.getNome());

        Transportadora transportadora = Transportadora.builder()
                .nome(request.getNome())
                .taxaPorKg(request.getTaxaPorKg())
                .build();

        transportadora = transportadoraRepository.save(transportadora);

        return new TransportadoraDto(transportadora.getId(), transportadora.getNome(), transportadora.getTaxaPorKg());
    }

    @Override
    public List<TransportadoraDto> listarTransportadoras() {
        log.info("Buscando todas as transportadoras");

        List<Transportadora> transportadoras = transportadoraRepository.findAll();

        return transportadoras.stream()
                .map(t -> new TransportadoraDto(t.getId(), t.getNome(), t.getTaxaPorKg()))
                .toList();
    }

    @Override
    public TransportadoraDto buscarTransportadora(UUID id) {
        log.info("Buscando transportadora com ID: {}", id);

        Transportadora transportadora = transportadoraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transportadora com ID {} não encontrada", id);
                    return new CepNaoEncontradoException("Transportadora não encontrada");
                });

        return new TransportadoraDto(transportadora.getId(), transportadora.getNome(), transportadora.getTaxaPorKg());
    }

    @Override
    public TransportadoraDto atualizarTransportadora(UUID id, TransportadoraDto request) {
        log.info("Atualizando transportadora com ID: {}", id);

        Transportadora transportadora = transportadoraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transportadora com ID {} não encontrada", id);
                    return new CepNaoEncontradoException("Transportadora não encontrada");
                });

        transportadora.setNome(request.getNome());
        transportadora.setTaxaPorKg(request.getTaxaPorKg());

        transportadora = transportadoraRepository.save(transportadora);

        return new TransportadoraDto(transportadora.getId(), transportadora.getNome(), transportadora.getTaxaPorKg());
    }

    @Override
    public void deletarTransportadora(UUID id) {
        log.info("Deletando transportadora com ID: {}", id);

        Transportadora transportadora = transportadoraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transportadora com ID {} não encontrada", id);
                    return new CepNaoEncontradoException("Transportadora não encontrada");
                });

        transportadoraRepository.deleteById(id);
    }
}
