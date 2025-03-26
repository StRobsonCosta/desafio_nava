package com.desafio.nava_log.application.port.out;

import com.desafio.nava_log.domain.model.Frete;

public interface FreteRepository {

    Frete save(Frete frete);
}
