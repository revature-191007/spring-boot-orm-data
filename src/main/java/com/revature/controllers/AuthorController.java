package com.revature.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Author;

@RestController
@RequestMapping("authors")
public class AuthorController {

	@PostMapping
	public Author create(@RequestBody @Valid Author author) {
		return author;
	}
}
