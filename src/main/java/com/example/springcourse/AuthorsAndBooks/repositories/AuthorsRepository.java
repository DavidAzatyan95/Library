package com.example.springcourse.AuthorsAndBooks.repositories;

import com.example.springcourse.AuthorsAndBooks.models.Author;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameContainingIgnoreCase(String name, PageRequest pageRequest);
    List<Author> findByNameContainingIgnoreCase(String name);
    List<Author> findAuthorByAge(int age);
    Optional<Author> deleteAuthorByName(String name);
}

