package com.example.demo.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookLibrary {
	
	private Map<String, Integer> books = new HashMap<>();
	private Map<String, List<String>> lendedBooks = new HashMap<>();

	public void lend(String book, String borrower) {
		if (!isBookAvailable(book)) {
			throw new IllegalStateException("Book is not available in our library");
		}
		
		removeBookFromShelf(book);
		addBookToLender(book, borrower);
	}

	private void removeBookFromShelf(String book) {
		Integer bookCount = books.get(book);
		books.put(book, bookCount-1);
	}
	
	public boolean isBookAvailable(String book) {
		return books.containsKey(book) && books.get(book) > 0;
	}

	private void addBookToLender(String book, String borrower) {
		if (lendedBooks.containsKey(borrower)) {
			lendedBooks.get(borrower).add(book);
		} else {
			List<String> borrowedBookList = new ArrayList<>();
			borrowedBookList.add(book);
			lendedBooks.put(borrower, borrowedBookList);
		}
	}


	public void addBookToLibrary(String book) {
		if (books.containsKey(book)) {
			Integer bookCount = books.get(book);
			books.put(book, bookCount + 1);
		} else {
			books.put(book, 1);
		}
	}

	public List<String> getBorrowedBookList(String borrower) {
		if (!lendedBooks.containsKey(borrower)) {
			return new ArrayList<>();
		}
		return lendedBooks.get(borrower);
	}

	public void returnBook(String book, String borrower) {
		if (!canReturnBook(book, borrower)) {
			throw new IllegalStateException("can't return book which does not belong to library or book which was not borrowed by user");
		}
		
		lendedBooks.get(borrower).remove(book);
		addBookToLibrary(book);
	}

	private boolean canReturnBook(String book, String borrower) {
		if (!lendedBooks.containsKey(borrower)) {
			return false;
		}
		return  lendedBooks.get(borrower).indexOf(book) != -1;
	}

	public Set<String> getLibraryBooks() {
		return books.keySet();
	}
}
