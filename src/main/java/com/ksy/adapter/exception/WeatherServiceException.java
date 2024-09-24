package com.ksy.adapter.exception;

public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(String message) {
        super(message);
    }
}

