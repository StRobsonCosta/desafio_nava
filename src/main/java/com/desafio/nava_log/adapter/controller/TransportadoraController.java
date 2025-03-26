package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transportadoras")
@RequiredArgsConstructor
public class TransportadoraController {

    private final TransportadoraUseCase transportadoraService;

    @PostMapping
    public ResponseEntity<TransportadoraDto>  criarTransportadora(@RequestBody TransportadoraDto request) {
        log.info("Criando uma nova transportadora");
        return ResponseEntity.status(HttpStatus.CREATED).body(transportadoraService.criarTransportadora(request));

    }

    @GetMapping("/listar")
    public ResponseEntity<List<TransportadoraDto>> listarTransportadoras() {
        log.info("Listando todas as transportadoras");
        return ResponseEntity.ok(transportadoraService.listarTransportadoras());
    }

    @GetMapping
    public ResponseEntity<TransportadoraDto> obterTransportadora(@RequestParam UUID id ) {
        log.info("Obtendo detalhes da transportadora com ID: {}", id);
        return ResponseEntity.ok(transportadoraService.buscarTransportadora(id));
    }

    @PutMapping
    public ResponseEntity<TransportadoraDto> atualizarTransportadora(@RequestParam UUID id, @RequestBody TransportadoraDto request) {
        log.info("Atualizando transportadora com ID: {}", id);
        return ResponseEntity.ok(transportadoraService.atualizarTransportadora(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTransportadora(@RequestParam UUID id) {
        log.info("Deletando transportadora com ID: {}", id);
        transportadoraService.deletarTransportadora(id);
        return ResponseEntity.noContent().build();
    }
}
