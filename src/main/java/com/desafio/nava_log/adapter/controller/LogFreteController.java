package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.application.service.LogFreteService;
import com.desafio.nava_log.domain.model.LogFrete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogFreteController {

    private final LogFreteService logFreteService;

    @GetMapping("/intervalo")
    public ResponseEntity<List<LogFrete>> buscarLogsPorIntervalo(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<LogFrete> logs = logFreteService.buscarLogsPorIntervalo(start, end);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/mensagem")
    public ResponseEntity<List<LogFrete>> buscarLogsPorMensagem(@RequestParam String mensagem) {
        List<LogFrete> logs = logFreteService.buscarLogsPorMensagem(mensagem);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<LogFrete>> buscarTodosLogs() {
        List<LogFrete> logs = logFreteService.buscarTodosLogs();
        return ResponseEntity.ok(logs);
    }
}
