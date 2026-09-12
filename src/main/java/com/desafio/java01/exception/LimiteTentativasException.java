package com.desafio.java01.exception;

public class LimiteTentativasException extends RuntimeException{

    public LimiteTentativasException() {
        super("Você excedeu o número máximo de tentativas. ");
    }
}
