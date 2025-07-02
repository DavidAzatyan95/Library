package com.example.springcourse.AuthorsAndBooks.controllers;

import com.example.springcourse.AuthorsAndBooks.dto.AuthorDto;
import com.example.springcourse.AuthorsAndBooks.dto.ResponseDto;
import com.example.springcourse.AuthorsAndBooks.models.Author;
import com.example.springcourse.AuthorsAndBooks.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseDto<AuthorDto> getAuthorById(@PathVariable("id") long id) {
        AuthorDto authorDto = authorService.getById(id);
        return new ResponseDto<>(authorDto, "Author was found", HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseDto<List<AuthorDto>> getAuthorByAge(@RequestParam(required = false) Integer age) {
        List<AuthorDto> authorsDto = authorService.getByAge(age);
        return new ResponseDto<>(authorsDto, "Author was found", HttpStatus.OK);
    }

    @PostMapping
    public ResponseDto<AuthorDto> createAuthor(@RequestBody Author author) {
        AuthorDto authorToCreate = authorService.save(author);
        return new ResponseDto<>(authorToCreate, "Author was created", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseDto<AuthorDto> deleteAuthor(@RequestBody Author author) {
        AuthorDto authorDto = null;
        if (author.getId() != null) {
            authorDto = authorService.deleteById(author);
        } else if (!author.getName().isBlank()) {
            authorDto = authorService.deleteByName(author);
        }
        if (authorDto == null) {
            return new ResponseDto<>("Author was not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseDto<>(authorDto, "Author was deleted", HttpStatus.OK);
    }
}
