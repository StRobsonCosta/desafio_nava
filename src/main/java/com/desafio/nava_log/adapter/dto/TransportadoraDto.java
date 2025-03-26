package com.desafio.nava_log.adapter.dto;

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
    private String nome;
    private BigDecimal taxaPorKg;
}
