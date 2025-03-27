package com.desafio.nava_log.application.port.in;

import com.desafio.nava_log.domain.model.LogFrete;

import java.time.LocalDateTime;
import java.util.List;

public interface LogFreteUseCase {

    void salvarLog(String mensagem);

    List<LogFrete> buscarLogsPorIntervalo(LocalDateTime start, LocalDateTime end);

    List<LogFrete> buscarLogsPorMensagem(String mensagem);

    List<LogFrete> buscarTodosLogs();
}
