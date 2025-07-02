package com.example.springcourse.AuthorsAndBooks.repositories;

import com.example.springcourse.AuthorsAndBooks.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByQueryIgnoreCase(String query);
}
