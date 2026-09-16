package com.desafio.java01.exception;

public class CodigoProdutoInvalidoException extends IllegalArgumentException {
    public CodigoProdutoInvalidoException(String message) {
        super(message);
    }
}
