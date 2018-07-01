package com.example.demo.library.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.library.entity.Book;
import com.example.demo.library.entity.BookLendingHistory;

@Repository
public class LibraryRepositoryFacade implements LibraryRepository {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookLendingHistoryRepository bookLendingHistoryRepository;
	
	@Override
	public Set<String> getBooks() {
		return bookRepository.findUniqueBooks();
	}

	@Override
	public Book removeFromShelf(String bookName) {
		List<Book> availableBooks = bookRepository.findAvailableByName(bookName);
		return availableBooks.iterator().next();
	}

	@Override
	public boolean isBookAvailable(String bookName) {
		List<Book> books = bookRepository.findAvailableByName(bookName);
		return ! books.isEmpty();
	}

	@Override
	public void addBook(String bookName) {
		 Book book = new Book(bookName);
		 bookRepository.save(book);
	}

	@Override
	public List<String> getBorrowedBookList(String borrower) {
		List<BookLendingHistory> borrowedNotReturnedBooks = bookLendingHistoryRepository.finBorrowedAndNotReturnedBooks(borrower);
		
		return borrowedNotReturnedBooks.stream()
				.map(result -> result.getBook().getName())
				.collect(Collectors.toList());
	}

	@Override
	public void assignBookToLender(Book book, String borrower) {
		BookLendingHistory blh = new BookLendingHistory();
		blh.setBook(book);
		blh.setPerson(borrower);
		blh.setLendingDate(new Date());
		
		bookLendingHistoryRepository.save(blh);
	}

	@Override
	public boolean canReturnBook(String bookName, String borrower) {
		List<String> borrowedNotReturnedBooks =  getBorrowedBookList(borrower);
		return borrowedNotReturnedBooks.contains(bookName);
	}

	@Override
	public void returnBook(String book, String borrower) {
		List<BookLendingHistory> borrowedNotReturnedBooks = bookLendingHistoryRepository.finBorrowedAndNotReturnedBooks(borrower);
		
		Optional<BookLendingHistory> borrowedBookToReturnOptional = borrowedNotReturnedBooks
		      .stream()
		      .filter(history -> book.equals(history.getBook().getName()))
		      .findAny();
		
		BookLendingHistory borrowedBookToReturn = borrowedBookToReturnOptional.get();
		borrowedBookToReturn.setReturnDate(new Date());
		
		bookLendingHistoryRepository.save(borrowedBookToReturn);
	}
}
