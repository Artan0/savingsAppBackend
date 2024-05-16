package com.example.savingsappbackend.models.exceptions;

public class InvalidArgumentsException extends RuntimeException {

    public InvalidArgumentsException() {
        super("Invalid argument.");
    }
}
