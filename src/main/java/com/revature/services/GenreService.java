package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Book;
import com.revature.entities.Genre;
import com.revature.repositories.GenreRepository;

@Service
public class GenreService {
	
	GenreRepository genreRepository;

	@Autowired
	public GenreService(GenreRepository genreRepository) {
		super();
		this.genreRepository = genreRepository;
	}

	@Transactional
	public Genre createGenre(Genre genre) {
		return genreRepository.create(genre);
	}

	public List<Book> getBooksByGenreId(int id) {
		Optional<List<Book>> optionalBooks = 
					genreRepository.getBooksByGenreId(id);
		return optionalBooks.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public Genre getGenre(int id) {
		Optional<Genre> optionalGenre = 
				genreRepository.getById(id);
		return optionalGenre.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

}
