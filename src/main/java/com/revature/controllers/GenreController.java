package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Book;
import com.revature.entities.Genre;
import com.revature.services.GenreService;

@RestController
@RequestMapping("genres")
public class GenreController {

	GenreService genreService;
	@Autowired
	public GenreController(GenreService genreService) {
		super();
		this.genreService = genreService;
	}	

	@GetMapping(value="/{id}", produces = "application/json")
	public Genre getGenre(@PathVariable int id) {
		return genreService.getGenre(id);
	}
	
	@GetMapping(value="/{id}", produces = "text/html")
	public String getGenreHtml(@PathVariable int id) {
		Genre genre = genreService.getGenre(id);
		return "<!DOCTYPE html><html><head></head><body><h1>#" 
				+ genre.getId() + " "
				+ genre.getName() + 
				"</h1></body></html>";
	}

	

	@GetMapping("/{id}/books")
	public List<Book> getGenreBooks(@PathVariable int id) {
		return genreService.getBooksByGenreId(id);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Genre createGenre(@RequestBody @Valid Genre genre) {
		return genreService.createGenre(genre);
	}
}
