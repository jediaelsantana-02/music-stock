package com.desafio.java01.exception;

public class LimiteMovimentacoes extends IllegalArgumentException {

    public LimiteMovimentacoes(String message) {
        super(message);
    }
}
