package com.kalki.blog.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponseWrapper<T> success(String message, T data) {
        return new ApiResponseWrapper<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponseWrapper<T> failure(String message) {
        return new ApiResponseWrapper<>(false, message, null, LocalDateTime.now());
    }
}

