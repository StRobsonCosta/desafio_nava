package com.desafio.nava_log.adapter.controller;

import com.desafio.nava_log.adapter.dto.FreteDto;
import com.desafio.nava_log.application.port.in.FreteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frete")
@RequiredArgsConstructor
public class FreteController {

    private final FreteUseCase freteUseCase;

    @GetMapping
    public ResponseEntity<FreteDto> calcularFrete(@RequestParam String cepOrigem, @RequestParam String cepDestino, @RequestParam Double peso) {
        FreteDto frete = freteUseCase.calcularFrete(cepOrigem, cepDestino, peso);

        return ResponseEntity.ok(frete);
    }

}
