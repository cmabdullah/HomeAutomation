package com.abdullah.home.automation.exception;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Getter
public class ApiErrorResponse {

    private final Long responseTime = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(6));
    private final Boolean hasError = true;
    private final String message;
    private final Map<String, String> errors;

    public ApiErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
