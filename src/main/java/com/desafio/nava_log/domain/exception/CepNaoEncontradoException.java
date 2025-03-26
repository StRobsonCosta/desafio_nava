package com.desafio.nava_log.domain.exception;

public class CepNaoEncontradoException extends RuntimeException {

    public CepNaoEncontradoException(String cep) {
        super("CEP não encontrado: " + cep);
    }

}
