package com.example.demo.library.repository;

import java.util.List;
import java.util.Set;

import com.example.demo.library.entity.Book;

public interface LibraryRepository {
	Set<String> getBooks();
	Book removeFromShelf(String bookNme);
	boolean isBookAvailable(String bookName);
	void addBook(String book);
	List<String> getBorrowedBookList(String borrower);
	void assignBookToLender(Book book, String borrower);
	boolean canReturnBook(String book, String borrower);
	void returnBook(String book, String borrower);
}
