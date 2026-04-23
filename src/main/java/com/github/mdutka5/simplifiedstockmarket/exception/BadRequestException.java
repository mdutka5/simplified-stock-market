package com.github.mdutka5.simplifiedstockmarket.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
