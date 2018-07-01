package com.example.demo.library;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.library.entity.Book;
import com.example.demo.library.repository.LibraryRepository;

@Service
public class BookLibrary {
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	public Set<String> getLibraryBooks() {
		return libraryRepository.getBooks();
	}

	@Transactional
	public void lend(String bookName, String borrower) {
		if (!isBookAvailable(bookName)) {
			throw new IllegalStateException("Book is not available in our library");
		}
		
		Book book = removeBookFromShelf(bookName);
		assignBookToLender(book, borrower);
	}

	private Book removeBookFromShelf(String book) {
		return libraryRepository.removeFromShelf(book);
	}
	
	public boolean isBookAvailable(String book) {
		return libraryRepository.isBookAvailable(book);
	}

	private void assignBookToLender(Book book, String borrower) {
		libraryRepository.assignBookToLender(book, borrower);
	}

	@Transactional
	public void addBookToLibrary(String book) {
		libraryRepository.addBook(book);
	}

	public List<String> getBorrowedBookList(String borrower) {
		return libraryRepository.getBorrowedBookList(borrower);
	}

	@Transactional
	public void returnBook(String book, String borrower) {
		if (!canReturnBook(book, borrower)) {
			throw new IllegalStateException("can't return book which does not belong to library or book which was not borrowed by user");
		}
		
		libraryRepository.returnBook(book, borrower);
	}

	private boolean canReturnBook(String book, String borrower) {
		return libraryRepository.canReturnBook(book, borrower);
	}

	void setLibraryRepository(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}
	
	
}
