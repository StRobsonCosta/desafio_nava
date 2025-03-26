package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.application.port.in.FreteUseCase;
import com.desafio.nava_log.domain.model.Frete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frete")
@RequiredArgsConstructor
public class FreteController {

    private final FreteUseCase freteUseCase;

//    @GetMapping
//    public Frete calcularFrete(@RequestParam String cepOrigem, @RequestParam String cepDestino, @RequestParam Double peso) {
//        return freteUseCase.calcularFrete(cepOrigem, cepDestino, peso);
//    }

    @GetMapping
    public ResponseEntity<FreteDto> calcularFrete(@RequestParam String cepOrigem, @RequestParam String cepDestino, @RequestParam Double peso) {
        Frete frete = freteUseCase.calcularFrete(cepOrigem, cepDestino, peso);

        String mensagem = String.format(
                "O frete do CEP %s para o CEP %s é de R$ %.2f",
                frete.getCepOrigem(), frete.getCepDestino(), frete.getValor()
        );

        return ResponseEntity.ok(new FreteDto(frete, mensagem));
    }

}
