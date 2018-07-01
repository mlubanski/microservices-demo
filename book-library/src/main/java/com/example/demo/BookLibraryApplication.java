package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.library.BookLibrary;

@SpringBootApplication
public class BookLibraryApplication {
	
	@Autowired
	private BookLibrary library;

	public static void main(String[] args) {
		SpringApplication.run(BookLibraryApplication.class, args);
	}
}
