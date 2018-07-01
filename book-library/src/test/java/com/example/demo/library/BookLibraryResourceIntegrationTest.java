package com.example.demo.library;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.BookLibraryApplication;;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BookLibraryApplication.class)
@AutoConfigureMockMvc
//you can define test property source which will overwrite application properties
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BookLibraryResourceIntegrationTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	BookLibraryResource libraryResource;
	
	@Test
	public void checkIsBookAvailableWhenBooksIsAvailable() throws Exception {
		//check availability for book which is available
		mvc.perform(get("/library/isAvailable").param("book", "Microservices"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isAvailable", is(true)));
	}

	@Test
	public void checkIsBookAvailableWhenBooksIsNotAvailable() throws Exception {
		//check availability for book which is not available
		mvc.perform(get("/library/isAvailable").param("book", "Not Existing One"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isAvailable", is(false)));
	}
	
	@Test
	public void checkTestDataInLibrary() throws Exception {
		//library has by default 3 books
		mvc.perform(get("/library/books"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$", hasItems("Microservices", "Lord of The Ring", "Spring Boot 2 Introduction")));
	}
	
	@Test
	public void checkReturningBookNotBelongingToLibrary() throws Exception {
		mvc.perform(
				get("/library/return")
				.param("book", "Not Existing Book")
				.param("person", "me"))
		.andExpect(status().is5xxServerError());
	}
	
	@Test
	public void testingLendingAndReturningBookFlow() throws Exception {
		String person = "me";
		String book = "Spring Boot 2 Introduction";
		
		lendBookToPerson(book, person);
		CheckThatbookIsNotAnymoreAvailable(book);
		bookAppearsOnBorrowerList(person, book);
		userReturnsBook(person, book);
		borrowerListIsEmpty(person);
		bookIsAvailableAgainInLibrary(book);
	}

	private void bookIsAvailableAgainInLibrary(String book) throws Exception {
		mvc.perform(get("/library/isAvailable").param("book", book))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isAvailable", is(true)));
	}

	private void borrowerListIsEmpty(String person) throws Exception {
		mvc.perform(
				get("/library/borrowers")
				.param("person", person))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}

	private void userReturnsBook(String person, String book) throws Exception {
		mvc.perform(
				get("/library/return")
				.param("book", book)
				.param("person", person))
		.andExpect(status().isOk());
	}

	private void bookAppearsOnBorrowerList(String person, String book) throws Exception {
		mvc.perform(
				get("/library/borrowers")
				.param("person", person))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$", hasItems(book)));
	}

	private void CheckThatbookIsNotAnymoreAvailable(String book) throws Exception {
		mvc.perform(get("/library/isAvailable").param("book", book))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isAvailable", is(false)));
	}

	private void lendBookToPerson(String book, String person) throws Exception {
		mvc.perform(
				get("/library/lend")
				.param("book", book)
				.param("person", person))
		.andExpect(status().isOk());
	}
}
