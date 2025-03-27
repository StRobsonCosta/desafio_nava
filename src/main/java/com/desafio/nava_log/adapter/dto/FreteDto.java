package com.desafio.nava_log.adapter.dto;

import com.desafio.nava_log.domain.model.Frete;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FreteDto {

    private UUID id;
    private String transportadora;
    private Double valorFrete;
    private String mensagem;

    private String cepOrigem;
    private String cepDestino;
    private Double peso;

    public FreteDto(Frete frete, String mensagem) {
        this.transportadora = frete.getTransportadora().getNome();
        this.valorFrete = frete.getValor().doubleValue();
        this.cepOrigem = frete.getCepOrigem();
        this.cepDestino = frete.getCepDestino();
        this.peso = Double.valueOf(String.valueOf(frete.getPeso()));
        this.mensagem = mensagem;
    }
}
