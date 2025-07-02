package com.example.springcourse.AuthorsAndBooks.controllers;

import com.example.springcourse.AuthorsAndBooks.dto.AuthorDto;
import com.example.springcourse.AuthorsAndBooks.dto.ResponseDto;
import com.example.springcourse.AuthorsAndBooks.services.AuthorServiceSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Resource {

    private AuthorServiceSearch service;

    @Autowired
    public Resource(AuthorServiceSearch service) {
        this.service = service;
    }

    @GetMapping("/authors")
    public ResponseDto<List<AuthorDto>> readAllAuthors(@RequestParam String name, @RequestParam(required = false)
    Integer page, @RequestParam(required = false) Integer authorsPerPage) {
        List<AuthorDto> authors = service.search(name, page, authorsPerPage);
        return new ResponseDto<>(authors, "List of authors", HttpStatus.OK);
    }
}

