package com.example.springcourse.AuthorsAndBooks.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    public BookDto(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
