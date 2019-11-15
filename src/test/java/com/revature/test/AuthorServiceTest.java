package com.revature.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.entities.Author;
import com.revature.repositories.AuthorRepository;
import com.revature.services.AuthorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

	@Mock
	private AuthorRepository mockAuthorRepository;
	
	@InjectMocks
	private AuthorService authorService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetByIdHappyPath() {
		int id = 5;
		Author author = new Author();
		
		when(mockAuthorRepository.findById(id))
			.thenReturn(Optional.of(author));
		
		Author returnedAuthor = authorService.getAuthorById(id);
		
		assertSame("Author returns optional with same author that repository provides", 
				author, returnedAuthor);
		
		// Verify that mockRepository was called correctly
		// with appropriate ID value
		verify(mockAuthorRepository).findById(id);
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testGetByIdNotFound() {
		int id = 5;
		Author author = new Author();
		
		when(mockAuthorRepository.findById(id))
			.thenReturn(Optional.empty());
		
		authorService.getAuthorById(id);
		
		fail("Exception should have been thrown due to empty optional");
		
		// Verify that mockRepository was called correctly
		// with appropriate ID value
	}
}
