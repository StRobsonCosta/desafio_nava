package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.application.port.in.FreteUseCase;
import com.desafio.nava_log.domain.model.Frete;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frete")
@RequiredArgsConstructor
public class FreteController {

    private final FreteUseCase freteUseCase;

    @GetMapping
    public Frete calcularFrete(@RequestParam String cepOrigem, @RequestParam String cepDestino, @RequestParam double peso) {
        return freteUseCase.calcularFrete(cepOrigem, cepDestino, peso);
    }

}
