package com.revature.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.advisor.ExceptionHandlerAdvisor;
import com.revature.controllers.AuthorController;
import com.revature.entities.Author;
import com.revature.services.AuthorService;

/**
 * Testing Controller Layer using TestMVC
 * 
 * We setup the controller for testing by providing mocked
 * dependencies.  We have the option of mocking the service layer
 * or mocking the repository layer. If we mock the repository
 * layer then we are not conducting unit tests, but rather 
 * larger scale integration tests.
 * 
 * If we mock the service layer, then we are conducting 
 * integration and/or unit tests. Really though, we are conducting
 * integration tests which cover how HTTP requests sent to your
 * server are resolved to handlers and how those handlers will
 * respond.
 * 
 *
 */
// Use the SpringRunner which allows for Spring bean injection
// while managing the test environment
@RunWith(SpringRunner.class)
@SpringBootTest // bootstraps Spring boot for test runs
@AutoConfigureMockMvc // provides autoconfiguration for MockMVC
public class AuthorControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	AuthorService mockAuthorService;
	
	@InjectMocks
	private AuthorController authorController;
	
	@Autowired
	ObjectMapper om;
	
	// Initializing mockito annotations (if they're not parsed)
	// Initializing mock MVC controller
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authorController)
				.setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();
	}
	
	@Test
	public void getByIdHappyPath() throws Exception {
		int id = 1;
		Author author = new Author();
		author.setFirstName("test");
		author.setLastName("test");
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockAuthorService.getAuthorById(1))
			.thenReturn(author);
		
		this.mockMvc.perform(get("/authors/" + id))
			.andExpect(content().contentTypeCompatibleWith("application/json"))
			.andExpect(content().json(om.writeValueAsString(author)))
			.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void getByIdNotFound() throws Exception {
		int id = 1;
		Author author = new Author();
		author.setFirstName("test");
		author.setLastName("test");
		
		// Stubbing the implementation of the getAuthorsById method
		when(mockAuthorService.getAuthorById(1))
			.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		this.mockMvc.perform(get("/authors/" + id))
			.andDo(print())
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void createAuthorTest() throws JsonProcessingException, Exception {
		Author author = new Author();
		author.setDateOfBirth(LocalDate.now().minus(1, ChronoUnit.DAYS));
		author.setFirstName("Steven");
		author.setLastName("Queen");

		Author returnedAuthor = new Author();
		returnedAuthor.setFirstName(author.getFirstName());
		returnedAuthor.setLastName(author.getLastName());
		returnedAuthor.setId(10);
		
		when(mockAuthorService.create(author))
			.thenReturn(returnedAuthor);
		
		this.mockMvc.perform(post("/authors/")
				// Include the request data
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(author)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(om.writeValueAsString(returnedAuthor)));
	}
	
}
