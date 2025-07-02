package com.example.springcourse.AuthorsAndBooks.repositories;

import com.example.springcourse.AuthorsAndBooks.dto.StatisticsRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRedisRepository extends CrudRepository<StatisticsRedis, Long> {
}
