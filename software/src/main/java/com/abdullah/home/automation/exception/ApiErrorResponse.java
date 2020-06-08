package com.abdullah.home.automation.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

public class ApiErrorResponse {

    private final Long responseTime = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(6));
    private final Boolean hasError = true;
    private final String message;
    private final Map<String, String> errors;

    public ApiErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
