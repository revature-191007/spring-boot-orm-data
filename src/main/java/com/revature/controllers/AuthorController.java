package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Author;
import com.revature.services.AuthorService;

@RestController
@RequestMapping("authors")
public class AuthorController {

	AuthorService authorService;

	@Autowired
	public AuthorController(AuthorService authorService) {
		super();
		this.authorService = authorService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Author create(@RequestBody @Valid Author author) {
		return authorService.create(author);
	}
	
	@GetMapping("/{id}")
	public Author getAuthorById(@PathVariable int id) {
		return authorService.getAuthorById(id);
	}
	
	
	@GetMapping
	public Page<Author> getAuthors(
			@RequestParam(required=false) String firstName,
			@RequestParam(required=false) String lastName,
			Pageable page) {
		if (firstName != null) {
			return authorService.getAuthorsByFirstName(firstName, page);
		}
		if (lastName != null) {
			return authorService.getAuthorsByLastName(lastName, page);
		}
		
		return authorService.getAuthors(page);
	}
	
}
