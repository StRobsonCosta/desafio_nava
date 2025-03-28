package com.desafio.nava_log.service;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.application.port.in.TransportadoraUseCase;
import com.desafio.nava_log.application.port.out.FreteRepository;
import com.desafio.nava_log.application.service.CepValidationService;
import com.desafio.nava_log.application.service.FreteCalculator;
import com.desafio.nava_log.application.service.FreteService;
import com.desafio.nava_log.domain.exception.FreteNaoEncontradoException;
import com.desafio.nava_log.domain.model.Frete;
import com.desafio.nava_log.domain.model.Transportadora;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.nava_log.application.service.DistanciaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FreteServiceTest {

    private static final String CEP_SP = "01001-000";
    private static final String CEP_RJ = "20040-020";

    @Mock
    private CepValidationService cepValidationService;

    @Mock
    private TransportadoraUseCase transportadoraUseCase;

    @Mock
    private FreteRepository freteRepository;

    @Mock
    private FreteCalculator freteCalculator;

    @Mock
    private DistanciaService distanciaService;

    @InjectMocks
    private FreteService freteService;

    @Test
    void deveCalcularFreteComSucesso() {
        Double peso = 20.0;

        Transportadora transportadora = new Transportadora();
        transportadora.setTaxaPorKg(BigDecimal.valueOf(5.0));

        doNothing().when(cepValidationService).validarCep(CEP_SP, CEP_RJ);
        when(distanciaService.calcularDistanciaPorCep(CEP_SP, CEP_RJ)).thenReturn(100.0);
        when(transportadoraUseCase.selecionarTransportadora(peso, CEP_SP, CEP_RJ)).thenReturn(transportadora);
        when(freteCalculator.calcularFrete(transportadora, peso, 100.0)).thenReturn(BigDecimal.valueOf(110.00));

        FreteDto resultado = freteService.calcularFrete(CEP_SP, CEP_RJ, peso);

        String esperado = "O frete do CEP 01001-000 para o CEP 20040-020 é de R$ 110";
        String resultadoObtido = resultado.getMensagem().replace(",00", "").replace(".00", "").trim();

        assertNotNull(resultado);
        assertEquals(esperado, resultadoObtido);
    }

    @Test
    void deveLancarExcecaoQuandoPesoForInvalido() {
        String cepOrigem = "01000-000";
        String cepDestino = "02000-000";
        Double pesoInvalido = -1.0;

        assertThrows(IllegalArgumentException.class, () ->
                freteService.calcularFrete(cepOrigem, cepDestino, pesoInvalido));
    }

    @Test
    void deveBuscarFretePeloIdComSucesso() {
        UUID id = UUID.randomUUID();

        final Frete frete = getFrete();

        when(freteRepository.findById(id)).thenReturn(Optional.of(frete));

        FreteDto resultado = freteService.buscarFretePeloId(id);

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCepOrigem());
        assertNotNull(resultado.getTransportadora());
    }

    @Test
    void deveLancarExcecaoQuandoFreteNaoForEncontradoPeloId() {
        UUID idInvalido = UUID.randomUUID();

        when(freteRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThrows(FreteNaoEncontradoException.class, () ->
                freteService.buscarFretePeloId(idInvalido));
    }

    @Test
    void deveBuscarFretesPorCepsComSucesso() {

        final Frete frete1 = getFrete();

        Frete frete2 = new Frete();
        frete2.setCepOrigem(CEP_SP);
        frete2.setCepDestino(CEP_RJ);
        frete2.setPeso(BigDecimal.valueOf(30.0));
        frete2.setValor(BigDecimal.valueOf(150.00));
        frete2.setTransportadora(getTransportadoraX());

        when(freteRepository.findByCepOrigemAndCepDestino(CEP_SP, CEP_RJ))
                .thenReturn(Arrays.asList(frete1, frete2));

        List<FreteDto> resultado = freteService.buscarFretePorCeps(CEP_SP, CEP_RJ);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(CEP_SP, resultado.get(0).getCepOrigem());
        assertEquals(CEP_RJ, resultado.get(1).getCepDestino());
    }

    @Test
    void deveBuscarTodosFretesComSucesso() {
        Frete frete1 = getFrete();

        Frete frete2 = new Frete();
        frete2.setCepOrigem("03000-000");
        frete2.setCepDestino("04000-000");
        frete2.setPeso(BigDecimal.valueOf(30.0));
        frete2.setValor(BigDecimal.valueOf(150.00));
        frete2.setTransportadora(getTransportadoraY());

        when(freteRepository.findAll()).thenReturn(Arrays.asList(frete1, frete2));

        List<FreteDto> resultado = freteService.buscarTodosFretes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    private static @NotNull Transportadora getTransportadoraX() {
        Transportadora transportadora = new Transportadora();
        transportadora.setNome("Transportadora X");
        transportadora.setTaxaPorKg(BigDecimal.valueOf(5.0));
        return transportadora;
    }

    private static @NotNull Transportadora getTransportadoraY() {
        Transportadora transportadora = new Transportadora();
        transportadora.setNome("Transportadora Y");
        transportadora.setTaxaPorKg(BigDecimal.valueOf(5.0));
        return transportadora;
    }

    private static @NotNull Frete getFrete() {
        Frete frete = new Frete();
        frete.setId(UUID.randomUUID());
        frete.setCepOrigem(CEP_SP);
        frete.setCepDestino(CEP_RJ);
        frete.setPeso(BigDecimal.valueOf(20.0));
        frete.setValor(BigDecimal.valueOf(110.00));
        frete.setTransportadora(getTransportadoraX());
        return frete;
    }

}
