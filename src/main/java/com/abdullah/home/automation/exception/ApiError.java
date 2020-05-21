package com.abdullah.home.automation.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Data
public class ApiError extends RuntimeException {

    private final String code;
    private final HttpStatus status;
    private Object[] args;
    private Map<String, String> errors = new HashMap<>();

    public static Supplier<? extends ApiError> createSingletonSupplier(String code, HttpStatus status){
        return () -> new ApiError(code, status);
    }

    public ApiError(String code, HttpStatus status){
        this.code = code;
        this.status = status;
    }

    public void addError(String key, String message){
        errors.put(key, message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
