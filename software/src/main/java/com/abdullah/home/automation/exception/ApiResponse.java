package com.abdullah.home.automation.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class ApiResponse<T> {
    private final Boolean hasError = false;
    private final Long responseTime = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(6));
    private T result;

    public ApiResponse(T body){
        result = body;
    }
}
