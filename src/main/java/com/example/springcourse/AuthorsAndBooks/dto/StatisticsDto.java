package com.example.springcourse.AuthorsAndBooks.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class StatisticsDto {
    private String query;

    private int numbers;

    public StatisticsDto(String query, int numbers) {
        this.query = query;
        this.numbers = numbers;
    }
}
