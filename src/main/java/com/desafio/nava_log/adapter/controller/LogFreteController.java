package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.application.service.LogFreteService;
import com.desafio.nava_log.domain.model.LogFrete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogFreteController {

    private final LogFreteService logFreteService;

    @GetMapping("/intervalo")
    @Operation(summary = "Obter Logs por Interválo de Tempo", description = "Retorna os detalhes de um Log específico")
    @ApiResponse(responseCode = "200", description = "Log encontrado")
    @ApiResponse(responseCode = "404", description = "Log não encontrado")
    public ResponseEntity<List<LogFrete>> buscarLogsPorIntervalo(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<LogFrete> logs = logFreteService.buscarLogsPorIntervalo(start, end);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/mensagem")
    @Operation(summary = "Obter Logs por Mensagem", description = "Retorna os detalhes de um Log específico")
    @ApiResponse(responseCode = "200", description = "Log encontrado")
    @ApiResponse(responseCode = "404", description = "Log não encontrado")
    public ResponseEntity<List<LogFrete>> buscarLogsPorMensagem(@RequestParam String mensagem) {
        List<LogFrete> logs = logFreteService.buscarLogsPorMensagem(mensagem);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/listar")
    @Operation(summary = "Obter Logs por Interválo de Tempo", description = "Retorna os detalhes de um Log específico")
    @ApiResponse(responseCode = "200", description = "Log encontrado")
    @ApiResponse(responseCode = "200", description = "Lista de Log encontrada", content = @Content(schema = @Schema(implementation = LogFrete.class)))
    public ResponseEntity<List<LogFrete>> buscarTodosLogs() {
        List<LogFrete> logs = logFreteService.buscarTodosLogs();
        return ResponseEntity.ok(logs);
    }
}
