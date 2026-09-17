package com.desafio.java01.exception;

public class NomeProdutoInvalidoException extends RuntimeException {
    public NomeProdutoInvalidoException(String message) {
        super(message);
    }
}
