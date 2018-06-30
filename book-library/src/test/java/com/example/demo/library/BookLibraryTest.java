package com.example.demo.library;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BookLibraryTest {
	
	private static final String USER = "Jan Kowalski";
	private static final String BOOK1 = "Microservices Demo";
	private static final String BOOK2 = "Microservices with Spring Boot 2";
	
	private BookLibrary library;
	
	@Before
	public void init() {
		library = new BookLibrary();
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantLendBookWhichIsNotAvailable() throws Exception {
		//given: book is not available in library
		
		//when
		library.lend("Not Available Book", USER);
		
		//then exception is thrown
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantLendTwoBooksWhichAvailabilityIsOne() throws Exception {
		//given
		library.addBookToLibrary(BOOK1);
		
		//when
		library.lend(BOOK1, USER);
		Assert.assertFalse(library.isBookAvailable(BOOK1));
		library.lend(BOOK1, USER);
		
		//then exception is thrown
	}
	
	@Test
	public void afterLendingBookPersonApperarOnListOfBorrowers() throws Exception {
		//given
		library.addBookToLibrary(BOOK1);
		library.addBookToLibrary(BOOK2);
		
		//when
		library.lend(BOOK1, USER);
		library.lend(BOOK2, USER);
		
		//then
		List<String> books = library.getBorrowedBookList(USER);
		Assert.assertEquals(2, books.size());
		Assert.assertTrue(books.indexOf(BOOK1) != -1);
		Assert.assertTrue(books.indexOf(BOOK2) != -1);
	}
	
	@Test
	public void whenUserDoesNotBorrowAnyBookHisBorrowedBookListIsEmpty() throws Exception {
		//given: users 
		
		//when
		List<String> books = library.getBorrowedBookList(USER);
		
		//then
		Assert.assertTrue(books.isEmpty());
	}
	
	@Test(expected=IllegalStateException.class)
	public void cantReturnBookWhichWasNotBorrowedByUser() throws Exception {
		//given
		Assert.assertFalse(library.isBookAvailable(BOOK1));
		
		//when
		library.returnBook(BOOK1, USER);
		
		//then exception is thronw
	}
	
	@Test
	public void userReturnsBorrowedBook() throws Exception {
		//given
		library.addBookToLibrary(BOOK1);
		library.lend(BOOK1, USER);
		
		//when
		library.returnBook(BOOK1, USER);
		
		//then
		List<String> books = library.getBorrowedBookList(USER);
		Assert.assertTrue(books.isEmpty());
		Assert.assertTrue(library.isBookAvailable(BOOK1));
	}
	
	@Test
	public void whenBookIsAddedToLibraryItOccurOnLibraryAvailableBookList() throws Exception {
		//given 
		library.addBookToLibrary(BOOK1);
		
		//when
		Set<String> libraryBooks = library.getLibraryBooks();		
		
		//then
		Assert.assertTrue(libraryBooks.contains(BOOK1));
	}
	
	@Test
	public void whenBookDoesNotExistInLibraryAvailabilityMethodReturnFalse() throws Exception {
		//given books does not exist in library
		
		//when
		boolean isAvailable = library.isBookAvailable("Not Existing Book");
		
		//then
		Assert.assertFalse(isAvailable);
	}
	
	@Test
	public void whenOnlyBookCopyIsLendedThanItIsNotAvailable() throws Exception {
		//given
		library.addBookToLibrary(BOOK1);
		library.lend(BOOK1, USER);
		
		//when
		boolean isAvailable = library.isBookAvailable(BOOK1);
		
		//then
		Assert.assertFalse(isAvailable);
	}
	
	@Test
	public void whenBookIsAddedToLibraryThanItIsAvailable() throws Exception {
		//given
		library.addBookToLibrary(BOOK1);
		
		//when
		boolean isAvailable = library.isBookAvailable(BOOK1);
		
		//then
		Assert.assertTrue(isAvailable);
	}
}
