package com.desafio.nava_log.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.desafio.nava_log.adapter.dto.TransportadoraDto;
import com.desafio.nava_log.application.port.in.DistanciaUseCase;
import com.desafio.nava_log.application.service.TransportadoraService;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import com.desafio.nava_log.domain.model.Transportadora;
import com.desafio.nava_log.infrastructure.repository.TransportadoraRepositoryImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TransportadoraServiceTest {

    private static final String CEP_SP = "01001-000";
    private static final String CEP_RJ = "20040-020";
    private static final String TRANSPORTADORA_1 = "Transportadora 1";
    private static final String TRANSPORTADORA_2 = "Transportadora 2";
    private static final String TRANSPORTADORA_TEST = "Transportadora Test";

    @Mock
    private TransportadoraRepositoryImp transportadoraRepository;

    @Mock
    private DistanciaUseCase distanciaUseCase;

    @InjectMocks
    private TransportadoraService transportadoraService;

    @Test
    void deveSelecionarTransportadoraComMenorCusto() {

        Transportadora transportadora1 = Transportadora.builder()
                .id(UUID.randomUUID())
                .nome(TRANSPORTADORA_1)
                .taxaPorKg(BigDecimal.valueOf(10))
                .cepTransportadora(CEP_SP)
                .build();
        Transportadora transportadora2 = Transportadora.builder()
                .id(UUID.randomUUID())
                .nome(TRANSPORTADORA_2)
                .taxaPorKg(BigDecimal.valueOf(12))
                .cepTransportadora(CEP_SP)
                .build();

        when(transportadoraRepository.findAll()).thenReturn(Arrays.asList(transportadora1, transportadora2));
        when(distanciaUseCase.calcularDistanciaGeografica(CEP_SP, CEP_RJ, transportadora1.getCepTransportadora()))
                .thenReturn(10.0);
        when(distanciaUseCase.calcularDistanciaGeografica(CEP_SP, CEP_RJ, transportadora2.getCepTransportadora()))
                .thenReturn(12.0);

        Transportadora result = transportadoraService.selecionarTransportadora(10, CEP_SP, CEP_RJ);

        assertEquals(transportadora1, result);
        verify(transportadoraRepository, times(1)).findAll();
        verify(distanciaUseCase, times(2)).calcularDistanciaGeografica(any(), any(), any());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumaTransportadoraDisponivel() {

        when(transportadoraRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> transportadoraService.selecionarTransportadora(10, CEP_SP, CEP_RJ));
    }

    @Test
    void deveCriarTransportadoraComSucesso() {
        TransportadoraDto request = new TransportadoraDto(null, TRANSPORTADORA_TEST, BigDecimal.valueOf(10), CEP_SP);
        Transportadora transportadora = Transportadora.builder()
                .id(UUID.randomUUID())
                .nome(request.getNome())
                .taxaPorKg(request.getTaxaPorKg())
                .cepTransportadora(request.getCepTransportadora())
                .build();

        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        TransportadoraDto result = transportadoraService.criarTransportadora(request);

        assertNotNull(result);
        assertEquals(request.getNome(), result.getNome());
        assertEquals(request.getTaxaPorKg(), result.getTaxaPorKg());
        assertEquals(request.getCepTransportadora(), result.getCepTransportadora());
        verify(transportadoraRepository, times(1)).save(any(Transportadora.class));
    }

    @Test
    void deveListarTransportadoras() {
        Transportadora transportadora1 = Transportadora.builder()
                .id(UUID.randomUUID())
                .nome(TRANSPORTADORA_1)
                .taxaPorKg(BigDecimal.valueOf(10))
                .cepTransportadora(CEP_SP)
                .build();
        Transportadora transportadora2 = Transportadora.builder()
                .id(UUID.randomUUID())
                .nome(TRANSPORTADORA_2)
                .taxaPorKg(BigDecimal.valueOf(12))
                .cepTransportadora(CEP_SP)
                .build();

        when(transportadoraRepository.findAll()).thenReturn(Arrays.asList(transportadora1, transportadora2));

        List<TransportadoraDto> result = transportadoraService.listarTransportadoras();

        assertEquals(2, result.size());
        assertEquals(TRANSPORTADORA_1, result.get(0).getNome());
        assertEquals(TRANSPORTADORA_2, result.get(1).getNome());
        verify(transportadoraRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTransportadoraPorId() {
        UUID id = UUID.randomUUID();
        Transportadora transportadora = Transportadora.builder()
                .id(id)
                .nome(TRANSPORTADORA_TEST)
                .taxaPorKg(BigDecimal.valueOf(10))
                .cepTransportadora(CEP_SP)
                .build();

        when(transportadoraRepository.findById(id)).thenReturn(Optional.of(transportadora));

        TransportadoraDto result = transportadoraService.buscarTransportadora(id);

        assertEquals(id, result.getId());
        assertEquals(TRANSPORTADORA_TEST, result.getNome());
        verify(transportadoraRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoTransportadoraNaoEncontrada() {
        UUID id = UUID.randomUUID();

        when(transportadoraRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CepNaoEncontradoException.class, () -> transportadoraService.buscarTransportadora(id));
    }

    @Test
    void deveAtualizarTransportadoraComSucesso() {
        UUID id = UUID.randomUUID();
        TransportadoraDto request = new TransportadoraDto(id, "Transportadora Atualizada", BigDecimal.valueOf(15), CEP_SP);
        Transportadora transportadora = Transportadora.builder()
                .id(id)
                .nome(TRANSPORTADORA_TEST)
                .taxaPorKg(BigDecimal.valueOf(10))
                .cepTransportadora(CEP_SP)
                .build();

        when(transportadoraRepository.findById(id)).thenReturn(Optional.of(transportadora));
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        TransportadoraDto result = transportadoraService.atualizarTransportadora(id, request);

        assertEquals(request.getNome(), result.getNome());
        assertEquals(request.getTaxaPorKg(), result.getTaxaPorKg());
        assertEquals(request.getCepTransportadora(), result.getCepTransportadora());
        verify(transportadoraRepository, times(1)).save(any(Transportadora.class));
    }

    @Test
    void deveDeletarTransportadoraComSucesso() {
        UUID id = UUID.randomUUID();
        Transportadora transportadora = Transportadora.builder()
                .id(id)
                .nome(TRANSPORTADORA_TEST)
                .taxaPorKg(BigDecimal.valueOf(10))
                .cepTransportadora(CEP_SP)
                .build();

        when(transportadoraRepository.findById(id)).thenReturn(Optional.of(transportadora));

        transportadoraService.deletarTransportadora(id);

        verify(transportadoraRepository, times(1)).deleteById(id);
    }

}
