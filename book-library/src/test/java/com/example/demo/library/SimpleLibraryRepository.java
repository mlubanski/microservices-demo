package com.example.demo.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.library.entity.Book;
import com.example.demo.library.repository.LibraryRepository;

public class SimpleLibraryRepository implements LibraryRepository{

	private Map<String, Integer> books = new HashMap<>();
	private Map<String, List<String>> lendedBooks = new HashMap<>();
	
	@Override
	public Set<String> getBooks() {
		return books.keySet();
	}

	@Override
	public boolean isBookAvailable(String bookName) {
		return books.containsKey(bookName) && books.get(bookName) > 0;
	}

	@Override
	public void addBook(String book) {
		if (books.containsKey(book)) {
			Integer bookCount = books.get(book);
			books.put(book, bookCount + 1);
		} else {
			books.put(book, 1);
		}	
	}

	@Override
	public List<String> getBorrowedBookList(String borrower) {
		if (!lendedBooks.containsKey(borrower)) {
			return new ArrayList<>();
		}
		return lendedBooks.get(borrower);
	}

	@Override
	public void assignBookToLender(Book book, String borrower) {
		if (lendedBooks.containsKey(borrower)) {
			lendedBooks.get(borrower).add(book.getName());
		} else {
			List<String> borrowedBookList = new ArrayList<>();
			borrowedBookList.add(book.getName());
			lendedBooks.put(borrower, borrowedBookList);
		}
	}

	@Override
	public Book removeFromShelf(String bookNme) {
		Integer bookCount = books.get(bookNme);
		books.put(bookNme, bookCount-1);
		
		return new Book(bookNme);
	}

	@Override
	public boolean canReturnBook(String book, String borrower) {
		if (!lendedBooks.containsKey(borrower)) {
			return false;
		}
		return lendedBooks.get(borrower).indexOf(book) != -1;
	}

	@Override
	public void returnBook(String book, String borrower) {
		lendedBooks.get(borrower).remove(book);
		addBook(book);
	}
}
