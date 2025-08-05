package com.kalki.blog.exceptions;

import com.kalki.blog.payloads.ApiResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseWrapper<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("Validation failed: " + errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleBadCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("Invalid username or password"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleUserNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("User not found"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleExpiredJwt(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("JWT token has expired"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleMalformedJwt(MalformedJwtException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("Invalid JWT token"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleSignatureException(SignatureException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("JWT signature does not match"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleApiException(ApiException ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Catch-all handler for unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                ApiResponseWrapper.failure("An unexpected error occurred: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
