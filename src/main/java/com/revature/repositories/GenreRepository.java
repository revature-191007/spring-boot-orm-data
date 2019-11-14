package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.entities.Book;
import com.revature.entities.Genre;

@Repository
public class GenreRepository {

	@Autowired(required = true)
	EntityManager em;
	
	public Optional<List<Book>> getBooksByGenreId(int id) {
		Session session = em.unwrap(Session.class);
		Genre genre = session.get(Genre.class, id);
		
		if (genre == null) 
			return Optional.empty();
		
		List<Book> books = genre.getBooks();
		Hibernate.initialize(books);
		return Optional.of(books);
	}


	public Genre create(Genre genre) {
		Session session = em.unwrap(Session.class);
		session.save(genre);
		return genre;
	}

	public Optional<Genre> getById(int id) {
		Session session = em.unwrap(Session.class);
		return Optional.ofNullable(session.get(Genre.class, id));
	}

}
