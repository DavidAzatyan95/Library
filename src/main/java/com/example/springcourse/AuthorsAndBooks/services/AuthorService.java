package com.example.springcourse.AuthorsAndBooks.services;

import com.example.springcourse.AuthorsAndBooks.dto.AuthorDto;
import com.example.springcourse.AuthorsAndBooks.dto.BookDto;
import com.example.springcourse.AuthorsAndBooks.models.Author;
import com.example.springcourse.AuthorsAndBooks.repositories.AuthorsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public AuthorDto getById(Long id) {
        AuthorDto authorDto = authorsRepository.findById(id)
                .map(this::convertToDto).orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
        return authorDto;
    }

    public List<AuthorDto> getByAge(Integer age) {
        List<AuthorDto> authorsDto = authorsRepository.findAuthorByAge(age).stream()
                .map(this::convertToDto).toList();
        return authorsDto;
    }

    @Transactional
    public AuthorDto save(Author author) {
        Author saveAuthor = authorsRepository.save(author);
        return convertToDto(saveAuthor);
    }

    @Transactional
    public AuthorDto deleteById(Author author) {
        AuthorDto authorDto = authorsRepository.findById(author.getId())
                .map(this::convertToDto).orElse(null);
        authorsRepository.deleteById(author.getId());
        return convertToDto(author);
    }

    @Transactional
    public AuthorDto deleteByName(Author author) {
        Optional<Author> aut = authorsRepository.deleteAuthorByName(author.getName());

        return convertToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setAge(author.getAge());
        authorDto.setBooks(author.getBooks().stream().map(book -> new BookDto(book.getId(), book.getName())).toList());
        return authorDto;
    }
}
