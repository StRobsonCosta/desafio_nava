package com.desafio.nava_log.service;

import com.desafio.nava_log.adapter.dto.EnderecoDto;
import com.desafio.nava_log.application.port.out.CepApiClient;
import com.desafio.nava_log.application.service.CepValidationService;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CepValidationServiceTest {

    @Mock
    private CepApiClient cepApiClient;

    @InjectMocks
    private CepValidationService cepValidationService;

    private static @NotNull EnderecoDto getEnderecoRJ() {
        return new EnderecoDto("20040-020", "Avenida Teste", "", "Cinelândia", "Rio de Janeiro", "RJ");
    }

    private static @NotNull EnderecoDto getEnderecoSP() {
        return new EnderecoDto("01001-000", "Rua Exemplo", "", "Sé", "São Paulo", "SP");
    }

    @Test
    void deveValidarCepComSucesso() {
        when(cepApiClient.consultarCep("01001-000")).thenReturn(getEnderecoSP());
        when(cepApiClient.consultarCep("20040-020")).thenReturn(getEnderecoRJ());

        assertDoesNotThrow(() -> cepValidationService.validarCep("01001-000", "20040-020"));
    }

    @Test
    void deveLancarExcecaoQuandoCepOrigemForInvalido() {
        when(cepApiClient.consultarCep("00000-000")).thenReturn(null);

        assertThrows(CepNaoEncontradoException.class, () ->
                cepValidationService.validarCep("00000-000", "20040-020")
        );
    }

    @Test
    void deveLancarExcecaoQuandoCepDestinoForInvalido() {
        when(cepApiClient.consultarCep("01001-000")).thenReturn(getEnderecoSP());
        when(cepApiClient.consultarCep("99999-999")).thenReturn(null);

        assertThrows(CepNaoEncontradoException.class, () ->
                cepValidationService.validarCep("01001-000", "99999-999")
        );
    }

    @Test
    void deveLancarExcecaoQuandoAmbosOsCepsSaoInvalidos() {

        assertThrows(CepNaoEncontradoException.class, () ->
                cepValidationService.validarCep("00000-000", "99999-999")
        );
    }
}
