package com.revature.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Author;

/**
 * Repository
 * CrudRepository
 * PagingAndSortingRepository
 * JpaRepository
 */

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

	@Query("from Author a WHERE a.firstName = :firstName")
	Page<Author> getAuthorsByFirstName(String firstName, Pageable page);
	
	/*
	 * Spring Data - Fluent Query API
	 */
	Page<Author> getAuthorsByLastName(String lastName, Pageable page);
}
