package com.desafio.nava_log.application.port.out;

import com.desafio.nava_log.domain.model.LogFrete;

import java.time.LocalDateTime;
import java.util.List;

public interface LogFreteRepository {
    LogFrete save(LogFrete log);

    List<LogFrete> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<LogFrete> findByMensagemContaining(String mensagem);

    List<LogFrete> findAll();
}
