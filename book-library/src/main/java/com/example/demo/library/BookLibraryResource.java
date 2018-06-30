package com.example.demo.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/library", produces = {"application/json"})
public class BookLibraryResource {

	@Autowired
	private BookLibrary library;
	
	@RequestMapping(path="books", method=RequestMethod.GET)
	public Set<String> getLibraryBooks() {
		return library.getLibraryBooks();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Map<String, Boolean> isBookAvailable(@RequestParam String book) {
		Map<String, Boolean> result = new HashMap<>();
		result.put("isAvailable", library.isBookAvailable(book));
		return result;
	}
	
	@RequestMapping(path="/lend", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void lendBook(@RequestParam String book, @RequestParam String person) {
		library.lend(book, person);
	}
	
	@RequestMapping(path="/return", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void returnBook(@RequestParam String book, @RequestParam String person) {
		library.returnBook(book, person);
	}
	
	@RequestMapping(path="/borrowers", method=RequestMethod.GET)
	public List<String> getBorrowedBookList(@RequestParam String person) {	
		return library.getBorrowedBookList(person);
	}
}
