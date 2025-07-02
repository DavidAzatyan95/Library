package com.example.springcourse.AuthorsAndBooks.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;


@Entity
@Data
@NoArgsConstructor
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "query")
    private String query;

    @Column(name = "number")
    private int numbers;

    public Statistics(String query) {
        this.query = query;
    }
}

