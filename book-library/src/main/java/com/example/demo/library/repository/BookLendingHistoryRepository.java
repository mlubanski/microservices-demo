package com.example.demo.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.library.entity.BookLendingHistory;

public interface BookLendingHistoryRepository extends CrudRepository<BookLendingHistory, Long>{

	@Query("select h from BookLendingHistory h where h.person=?1 and h.returnDate is null")
	List<BookLendingHistory> finBorrowedAndNotReturnedBooks(String borrower);

}
