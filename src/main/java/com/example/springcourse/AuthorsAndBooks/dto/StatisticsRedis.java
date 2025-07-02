package com.example.springcourse.AuthorsAndBooks.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Statistic")
@Data
@NoArgsConstructor
public class StatisticsRedis {

    @org.springframework.data.annotation.Id
    private int id;

    private String query;
    private int numbers;

    public StatisticsRedis(String query) {
        this.query = query;
    }
}
