package com.desafio.nava_log.application.service;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.application.port.in.DistanciaUseCase;
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
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransportadoraService implements TransportadoraUseCase {

    private final TransportadoraRepositoryImp transportadoraRepository;

    private final DistanciaUseCase distanciaUseCase;

    @Override
    public Transportadora selecionarTransportadora(double peso, String cepOrigem, String cepDestino) {

        return transportadoraRepository.findAll().stream()
                .filter(t -> Objects.nonNull(t.getCepTransportadora()))
                .min(Comparator.comparing(t -> {
                    double distancia = distanciaUseCase.calcularDistanciaGeografica(cepOrigem, cepDestino, t.getCepTransportadora());
                    return t.getTaxaPorKg().doubleValue() + distancia;
                }))
                .orElseThrow(() -> new RuntimeException("Nenhuma transportadora disponível para os CEPs informados."));

    }

    @Transactional
    @Override
    public TransportadoraDto criarTransportadora(TransportadoraDto request) {
        log.info("Iniciando a criação de uma nova transportadora: {}", request.getNome());

        Transportadora transp = Transportadora.builder()
                .nome(request.getNome())
                .taxaPorKg(request.getTaxaPorKg())
                .build();

        transp = transportadoraRepository.save(transp);

        return new TransportadoraDto(transp.getId(), transp.getNome(), transp.getTaxaPorKg(), transp.getCepTransportadora());
    }

    @Override
    public List<TransportadoraDto> listarTransportadoras() {
        log.info("Buscando todas as transportadoras");

        List<Transportadora> transportadoras = transportadoraRepository.findAll();

        return transportadoras.stream()
                .map(t -> new TransportadoraDto(t.getId(), t.getNome(), t.getTaxaPorKg(), t.getCepTransportadora()))
                .toList();
    }

    @Override
    public TransportadoraDto buscarTransportadora(UUID id) {
        log.info("Buscando transportadora com ID: {}", id);

        Transportadora transp = transportadoraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transportadora com ID {} não encontrada", id);
                    return new CepNaoEncontradoException("Transportadora não encontrada");
                });

        return new TransportadoraDto(transp.getId(), transp.getNome(), transp.getTaxaPorKg(), transp.getCepTransportadora());
    }

    @Override
    public TransportadoraDto atualizarTransportadora(UUID id, TransportadoraDto request) {
        log.info("Atualizando transportadora com ID: {}", id);

        Transportadora transp = transportadoraRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transportadora com ID {} não encontrada", id);
                    return new CepNaoEncontradoException("Transportadora não encontrada");
                });

        transp.setNome(request.getNome());
        transp.setTaxaPorKg(request.getTaxaPorKg());
        transp.setCepTransportadora(request.getCepTransportadora());

        transp = transportadoraRepository.save(transp);

        return new TransportadoraDto(transp.getId(), transp.getNome(), transp.getTaxaPorKg(), transp.getCepTransportadora());
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
