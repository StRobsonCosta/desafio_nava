package com.desafio.nava_log.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "fretes")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Frete implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String cepOrigem;

    @Column(nullable = false)
    private String cepDestino;

    @Column(nullable = false)
    private BigDecimal peso;

    @Column
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "transportadora_id")
    private Transportadora transportadora;

}
