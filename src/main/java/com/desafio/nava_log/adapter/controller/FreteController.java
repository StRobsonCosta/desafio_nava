package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.application.port.in.FreteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/frete")
@RequiredArgsConstructor
@Tag(name = "Frete", description = "API para cálculo de frete entre dois CEPs")
public class FreteController {

    private final FreteUseCase freteUseCase;

    @GetMapping
    @Operation(summary = "Calcula o frete entre dois CEPs", description = "Este endpoint calcula o valor do frete com base no CEP de origem, CEP de destino e o peso do item.")
    @ApiResponse(responseCode = "200", description = "Cálculo do frete realizado com sucesso", content = @Content(schema = @Schema(implementation = FreteDto.class)))
    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content(mediaType = "application/json"))
    public ResponseEntity<FreteDto> calcularFrete(@RequestParam String cepOrigem, @RequestParam String cepDestino, @RequestParam Double peso) {
        log.info("Iniciando Requisição do Cálculo de Frete");

        FreteDto frete = freteUseCase.calcularFrete(cepOrigem, cepDestino, peso);

        return ResponseEntity.ok(frete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca frete pelo ID", description = "Este endpoint busca um frete a partir do ID informado.")
    @ApiResponse(responseCode = "200", description = "Frete encontrado com sucesso", content = @Content(schema = @Schema(implementation = FreteDto.class)))
    @ApiResponse(responseCode = "404", description = "Frete não encontrado", content = @Content(mediaType = "application/json"))
    public ResponseEntity<FreteDto> buscarFretePeloId(@PathVariable UUID id) {
        log.info("Buscando frete pelo ID: {}", id);

        FreteDto freteDto = freteUseCase.buscarFretePeloId(id);

        return ResponseEntity.ok(freteDto);
    }

    @GetMapping("/ceps")
    @Operation(summary = "Busca fretes pelos CEPs", description = "Este endpoint busca fretes com base no CEP de origem e destino.")
    @ApiResponse(responseCode = "200", description = "Fretes encontrados com sucesso", content = @Content(schema = @Schema(implementation = FreteDto.class)))
    @ApiResponse(responseCode = "404", description = "Nenhum frete encontrado", content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<FreteDto>> buscarFretePorCeps(@RequestParam String cepOrigem, @RequestParam String cepDestino) {
        log.info("Buscando fretes para os CEPs: origem={}, destino={}", cepOrigem, cepDestino);

        List<FreteDto> freteDtos = freteUseCase.buscarFretePorCeps(cepOrigem, cepDestino);

        return ResponseEntity.ok(freteDtos);
    }

    @GetMapping("/todos")
    @Operation(summary = "Busca todos os fretes", description = "Este endpoint retorna todos os fretes cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Fretes encontrados com sucesso", content = @Content(schema = @Schema(implementation = FreteDto.class)))
    public ResponseEntity<List<FreteDto>> buscarTodosFretes() {
        log.info("Buscando todos os fretes.");

        List<FreteDto> freteDtos = freteUseCase.buscarTodosFretes();

        return ResponseEntity.ok(freteDtos);
    }

}
