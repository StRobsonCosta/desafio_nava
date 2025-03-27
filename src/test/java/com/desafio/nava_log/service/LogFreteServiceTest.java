package com.desafio.nava_log.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.desafio.nava_log.application.port.out.LogFreteRepository;
import com.desafio.nava_log.application.service.LogFreteService;
import com.desafio.nava_log.domain.model.LogFrete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LogFreteServiceTest {

    @Mock
    private LogFreteRepository logFreteRepository;

    @InjectMocks
    private LogFreteService logFreteService;

    @Test
    void deveSalvarLogComSucesso() {
        String mensagem = "Mensagem de log";
        LocalDateTime timestampEsperado = LocalDateTime.now();

        LogFrete logEsperado = LogFrete.builder()
                .mensagem(mensagem)
                .timestamp(timestampEsperado)
                .build();

        logFreteService.salvarLog(mensagem);

        ArgumentCaptor<LogFrete> captor = ArgumentCaptor.forClass(LogFrete.class);

        verify(logFreteRepository, times(1)).save(captor.capture());

        LogFrete logSalvo = captor.getValue();

        assertEquals(logEsperado.getMensagem(), logSalvo.getMensagem());

        assertNotNull(logSalvo.getTimestamp());

        assertTrue(Duration.between(logSalvo.getTimestamp(), timestampEsperado).toMillis() < 1000);
    }


    @Test
    void deveBuscarLogsPorIntervalo() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        LogFrete log1 = LogFrete.builder().mensagem("Log 1").timestamp(start).build();
        LogFrete log2 = LogFrete.builder().mensagem("Log 2").timestamp(end).build();
        List<LogFrete> logs = Arrays.asList(log1, log2);

        when(logFreteRepository.findByTimestampBetween(start, end)).thenReturn(logs);

        List<LogFrete> result = logFreteService.buscarLogsPorIntervalo(start, end);

        assertEquals(2, result.size());
        verify(logFreteRepository, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void deveBuscarLogsPorMensagem() {
        String mensagem = "Log de erro";
        LogFrete log = LogFrete.builder().mensagem(mensagem).timestamp(LocalDateTime.now()).build();
        List<LogFrete> logs = Arrays.asList(log);

        when(logFreteRepository.findByMensagemContaining(mensagem)).thenReturn(logs);

        List<LogFrete> result = logFreteService.buscarLogsPorMensagem(mensagem);

        assertEquals(1, result.size());
        assertEquals(mensagem, result.get(0).getMensagem());
        verify(logFreteRepository, times(1)).findByMensagemContaining(mensagem);
    }

    @Test
    void deveBuscarTodosLogs() {
        LogFrete log1 = LogFrete.builder().mensagem("Log 1").timestamp(LocalDateTime.now()).build();
        LogFrete log2 = LogFrete.builder().mensagem("Log 2").timestamp(LocalDateTime.now()).build();
        List<LogFrete> logs = Arrays.asList(log1, log2);

        when(logFreteRepository.findAll()).thenReturn(logs);

        List<LogFrete> result = logFreteService.buscarTodosLogs();

        assertEquals(2, result.size());
        verify(logFreteRepository, times(1)).findAll();
    }
}
