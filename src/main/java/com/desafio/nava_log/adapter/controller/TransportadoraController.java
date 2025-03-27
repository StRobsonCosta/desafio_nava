package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@Slf4j
@RestController
@RequestMapping("/transportadoras")
@RequiredArgsConstructor
@Tag(name = "Transportadora", description = "Gerenciamento de Transportadoras") // Nome no Swagger
public class TransportadoraController {

    private final TransportadoraUseCase transportadoraService;

    @PostMapping
    @Operation(summary = "Criar uma nova transportadora", description = "Endpoint para criar uma transportadora no sistema")
    @ApiResponse(responseCode = "201", description = "Transportadora criada com sucesso", content = @Content(schema = @Schema(implementation = TransportadoraDto.class)))
    public ResponseEntity<TransportadoraDto> criarTransportadora(@RequestBody TransportadoraDto request) {
        log.info("Criando uma nova transportadora");
        return ResponseEntity.status(HttpStatus.CREATED).body(transportadoraService.criarTransportadora(request));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as transportadoras", description = "Retorna uma lista de transportadoras cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de transportadoras retornada com sucesso", content = @Content(schema = @Schema(implementation = TransportadoraDto.class)))
    public ResponseEntity<List<TransportadoraDto>> listarTransportadoras() {
        log.info("Listando todas as transportadoras");
        return ResponseEntity.ok(transportadoraService.listarTransportadoras());
    }

    @GetMapping
    @Operation(summary = "Obter uma transportadora pelo ID", description = "Retorna os detalhes de uma transportadora específica")
    @ApiResponse(responseCode = "200", description = "Transportadora encontrada", content = @Content(schema = @Schema(implementation = TransportadoraDto.class)))
    @ApiResponse(responseCode = "404", description = "Transportadora não encontrada")
    public ResponseEntity<TransportadoraDto> obterTransportadora(
            @Parameter(description = "ID da transportadora", required = true) @RequestParam UUID id) {
        log.info("Obtendo detalhes da transportadora com ID: {}", id);
        return ResponseEntity.ok(transportadoraService.buscarTransportadora(id));
    }

    @PutMapping
    @Operation(summary = "Atualizar transportadora", description = "Atualiza os dados de uma transportadora existente")
    @ApiResponse(responseCode = "200", description = "Transportadora atualizada com sucesso", content = @Content(schema = @Schema(implementation = TransportadoraDto.class)))
    @ApiResponse(responseCode = "404", description = "Transportadora não encontrada")
    public ResponseEntity<TransportadoraDto> atualizarTransportadora(
            @Parameter(description = "ID da transportadora", required = true) @RequestParam UUID id,
            @RequestBody TransportadoraDto request) {
        log.info("Atualizando transportadora com ID: {}", id);
        return ResponseEntity.ok(transportadoraService.atualizarTransportadora(id, request));
    }

    @DeleteMapping
    @Operation(summary = "Deletar transportadora", description = "Remove uma transportadora do sistema")
    @ApiResponse(responseCode = "204", description = "Transportadora deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Transportadora não encontrada")
    public ResponseEntity<Void> deletarTransportadora(
            @Parameter(description = "ID da transportadora", required = true) @RequestParam UUID id) {
        log.info("Deletando transportadora com ID: {}", id);
        transportadoraService.deletarTransportadora(id);
        return ResponseEntity.noContent().build();
    }
}
