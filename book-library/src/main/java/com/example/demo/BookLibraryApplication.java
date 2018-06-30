package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.library.BookLibrary;

@SpringBootApplication
public class BookLibraryApplication {
	
	@Autowired
	private BookLibrary library;
	
	@PostConstruct
	public void init() {
		library.addBookToLibrary("Microservices");
		library.addBookToLibrary("Microservices");
		library.addBookToLibrary("Spring Boot 2 Introduction");
		library.addBookToLibrary("Lord of The Ring");
	}

	public static void main(String[] args) {
		SpringApplication.run(BookLibraryApplication.class, args);
	}
}
