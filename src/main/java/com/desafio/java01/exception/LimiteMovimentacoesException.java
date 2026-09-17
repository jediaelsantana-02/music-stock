package com.desafio.java01.exception;

public class LimiteMovimentacoesException extends IllegalArgumentException {

    public LimiteMovimentacoesException(String message) {
        super(message);
    }
}
