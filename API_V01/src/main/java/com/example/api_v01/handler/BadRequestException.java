package com.example.api_v01.handler;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}
