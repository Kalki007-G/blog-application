package com.kalki.blog.exceptions;

public class ApiException extends RuntimeException{
    private boolean success;
    public ApiException() {
        super();
    }
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, boolean success) {
        super(message);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
