package com.abdullah.home.automation.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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


    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", args=" + Arrays.toString(args) +
                ", errors=" + errors +
                '}';
    }
}
