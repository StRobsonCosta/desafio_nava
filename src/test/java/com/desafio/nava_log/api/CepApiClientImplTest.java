package com.desafio.nava_log.api;

import com.desafio.nava_log.adapter.dto.EnderecoDto;
import com.desafio.nava_log.application.port.out.CepApiClient;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CepApiClientImplTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private CepApiClient cepApiClient;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(9090));
        wireMockServer.start();
        WireMock.configureFor("localhost", 9090);
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    void deveRetornarEnderecoAoConsultarCep() {
        wireMockServer.stubFor(WireMock.get("/cep/01001-000")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "cep": "01001-000",
                                "logradouro": "Praça da Sé",
                                "bairro": "Sé",
                                "localidade": "São Paulo",
                                "uf": "SP"
                            }
                        """)
                        .withStatus(200)));

        EnderecoDto endereco = cepApiClient.consultarCep("01001-000");

        assertNotNull(endereco);
        assertEquals("01001-000", endereco.getCep());
        assertEquals("Praça da Sé", endereco.getLogradouro());
    }

    @Test
    void deveLancarExcecaoQuandoCepNaoForEncontrado() {
        wireMockServer.stubFor(WireMock.get("/cep/99999-999")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)));

        CepNaoEncontradoException exception = assertThrows(CepNaoEncontradoException.class, () -> {
            cepApiClient.consultarCep("99999-999");
        });

        assertEquals("CEP não encontrado: CEP não encontrado: 99999-999", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoApiRetornarErroInterno() {
        wireMockServer.stubFor(WireMock.get("/cep/01001-000")
                .willReturn(WireMock.aResponse()
                        .withStatus(500)));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cepApiClient.consultarCep("01001-000");
        });

        assertTrue(exception.getMessage().contains("Erro ao consultar CEP"));
    }
}