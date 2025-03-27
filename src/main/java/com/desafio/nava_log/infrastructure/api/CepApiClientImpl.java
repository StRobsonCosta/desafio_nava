package com.desafio.nava_log.infrastructure.api;

import com.desafio.nava_log.adapter.dto.EnderecoDto;
import com.desafio.nava_log.application.port.out.CepApiClient;
import com.desafio.nava_log.application.service.LogFreteService;
import com.desafio.nava_log.domain.exception.CepNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CepApiClientImpl implements CepApiClient {

    private static final String BASE_URL = "http://localhost:9090"; // WireMock
    private final RestTemplate restTemplate;
    private final LogFreteService logFreteService;

    @Override
    public EnderecoDto consultarCep(String cep) {
        log.info("Consultando endereço para o CEP: {}", cep);

        try {
            URI baseUri = URI.create(BASE_URL);
            String url = UriComponentsBuilder.fromUri(baseUri)
                    .pathSegment("cep", cep)
                    .toUriString();


            EnderecoDto endereco = restTemplate.getForObject(url, EnderecoDto.class);

            if (Objects.isNull(endereco)) {
                logFreteService.salvarLog(String.format("CEP não encontrado: %s", cep));
                throw new CepNaoEncontradoException("CEP não encontrado: " + cep);
            }

            logFreteService.salvarLog(String.format("Frete calculado: Cep=%s, Logradouro=%s, Cidade=%s",
                    endereco.getCep(), endereco.getLogradouro(), endereco.getLocalidade()));

            return endereco;
        } catch (HttpClientErrorException.NotFound e) {
            log.error("CEP não encontrado: {}", cep);
            logFreteService.salvarLog(String.format("CEP não encontrado: %s", cep));
            throw new CepNaoEncontradoException("CEP não encontrado: " + cep);
        } catch (Exception e) {
            log.error("Erro ao consultar CEP/ CEP Inválido: {}", e.getMessage());
            logFreteService.salvarLog(String.format("CEP não encontrado: %s", cep));
            throw new RuntimeException("Erro ao consultar CEP/ CEP Inválido", e);
        }
    }
}
