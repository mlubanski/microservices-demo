package com.example.demo.library.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.library.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	@Query("select b.name from Book b group by b.name")
	Set<String> findUniqueBooks();

	List<Book> findByName(String name);


	//select b.* from BOOK  b EXCEPT select b.* from BOOK b join BOOK_LENDING_HISTORY h on b.id = h.book_Id where b.name = 'Spring Boot 2 Introduction';
	//@Query("(SELECT b from Book b) EXCEPT (select b from Book b join BookLendingHistory h on b.id = h.book.id where b.name = ?1 )")
	
	//select b.* from BOOK b where b.id not in (select h.book_id from BOOK_LENDING_HISTORY h where return_date is null);
	@Query("select b from Book b where b.id not in (select h.book.id from BookLendingHistory h where h.book.name = ?1 and h.returnDate is null) and b.name = ?1")
	List<Book> findAvailableByName(String bookName);
}
