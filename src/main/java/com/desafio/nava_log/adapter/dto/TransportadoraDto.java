package com.desafio.nava_log.adapter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportadoraDto {

    private UUID id;
    @NotNull
    private String nome;
    @NotNull
    private BigDecimal taxaPorKg;
    @NotNull
    private String cepTransportadora;
}
