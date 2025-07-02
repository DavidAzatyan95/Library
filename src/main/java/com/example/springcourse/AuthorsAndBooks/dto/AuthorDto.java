package com.example.springcourse.AuthorsAndBooks.dto;

import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private Integer age;
    private List<BookDto> books;

    public AuthorDto() {
    }
}
