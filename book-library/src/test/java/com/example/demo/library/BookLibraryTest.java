package com.example.demo.library;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BookLibraryTest {
	private BookLibrary library;
	
	@Before
	public void init() {
		library = new BookLibrary();
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantLendBookWhichIsNotAvailable() throws Exception {
		//given: book is not available in library
		
		//when
		library.lend("Not Available Book", "me");
		
		//then exception is thrown
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantLendTwoBooksWhichAvailabilityIsOne() throws Exception {
		//given
		library.addBookToLibrary("Microservices");
		
		//when
		library.lend("Microservices", "me");
		Assert.assertTrue(true);
		library.lend("Microservices", "me");
		
		//then exception is thrown
	}
	
	@Test
	public void afterLendingBookPersonApperarOnListOfBorrowers() throws Exception {
		//given
		library.addBookToLibrary("Microservices");
		library.addBookToLibrary("Reactive Programming");
		
		//when
		library.lend("Microservices", "me");
		library.lend("Reactive Programming", "me");
		
		//then
		List<String> books = library.getBorrowedBookList("me");
		Assert.assertEquals(2, books.size());
		Assert.assertTrue(books.indexOf("Microservices") != -1);
		Assert.assertTrue(books.indexOf("Reactive Programming") != -1);
	}
	
	@Test
	public void whenUserDoesNotBorrowAnyBookHisBorrowedBookListIsEmpty() throws Exception {
		//given: users 
		
		//when
		List<String> books = library.getBorrowedBookList("me");
		
		//then
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantReturnBookWhichWasNotBorrowedByUser() throws Exception {
		//given
		
		//when
		library.returnBook("Microservices", "me");
		
		//then exception is thronw
	}
	
	@Test
	public void userReturnsBorrowedBook() throws Exception {
		//given
		library.addBookToLibrary("Microservices");
		library.lend("Microservices", "me");
		
		//when
		library.returnBook("Microservices", "me");
		
		//then
		List<String> books = library.getBorrowedBookList("me");
		Assert.assertTrue(books.isEmpty());
	}
}
