package com.example.springcourse.AuthorsAndBooks.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ResponseDto<T> {
    private T data;
    private String message;
    private HttpStatus status;

    public ResponseDto(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public ResponseDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
