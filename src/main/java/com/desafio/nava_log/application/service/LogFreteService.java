package com.desafio.nava_log.application.service;

import com.desafio.nava_log.application.port.in.LogFreteUseCase;
import com.desafio.nava_log.application.port.out.LogFreteRepository;
import com.desafio.nava_log.domain.model.LogFrete;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogFreteService implements LogFreteUseCase {

    private final LogFreteRepository logFreteRepository;

    @Override
    public void salvarLog(String mensagem) {
        LogFrete log = LogFrete.builder()
                .mensagem(mensagem)
                .timestamp(LocalDateTime.now())
                .build();

        logFreteRepository.save(log);
    }

    @Override
    public List<LogFrete> buscarLogsPorIntervalo(LocalDateTime start, LocalDateTime end) {
        return logFreteRepository.findByTimestampBetween(start, end);
    }

    @Override
    public List<LogFrete> buscarLogsPorMensagem(String mensagem) {
        return logFreteRepository.findByMensagemContaining(mensagem);
    }

    @Override
    public List<LogFrete> buscarTodosLogs() {
        return logFreteRepository.findAll();
    }
}
