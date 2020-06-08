package com.abdullah.home.automation.exception;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ApiResponse<T> {
    private final Boolean hasError = false;
    private final Long responseTime = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(6));
    private T result;

    public ApiResponse(T body){
        result = body;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public T getResult() {
        return result;
    }
}
