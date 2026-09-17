package com.desafio.java01.exception;

public class PrecoProdutoInvalidoException extends RuntimeException {
    public PrecoProdutoInvalidoException(String message) {
        super(message);
    }
}
