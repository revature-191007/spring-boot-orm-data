package com.revature.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Author;
import com.revature.repositories.AuthorRepository;

@Service
public class AuthorService {

	AuthorRepository authorRepository;

	@Autowired
	public AuthorService(AuthorRepository authorRepository) {
		super();
		this.authorRepository = authorRepository;
	}

	public Author create(@Valid Author author) {
		return authorRepository.save(author);
	}

	public Author getAuthorById(int id) {
		return authorRepository.findById(id).orElseThrow(() -> 
			new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public Page<Author> getAuthorsByFirstName(String firstName, Pageable page) {
		return authorRepository.getAuthorsByFirstName(firstName, page);
	}

	public Page<Author> getAuthorsByLastName(String lastName, Pageable page) {
		return authorRepository.getAuthorsByLastName(lastName, page);
	}

	public Page<Author> getAuthors(Pageable page) {
		return authorRepository.findAll(page);
	}
}
