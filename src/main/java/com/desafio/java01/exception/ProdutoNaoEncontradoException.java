package com.desafio.java01.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
