package com.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Book;
import com.library.service.BookService;
import com.library.ws.request.BookWSData;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private BookService bookService;

	// Constructor Injection
	public BookController(BookService mBookService) {
		bookService = mBookService;
	}

	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody BookWSData book) {
		return ResponseEntity.ok(bookService.addBook(book));
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/author/{author}")
	public List<Book> getBooksByAuthor(@PathVariable String author) {
		return bookService.getBooksByAuthor(author);
	}

	@GetMapping("/category/{categoryName}")
	public List<Book> getBooksByCategory(@PathVariable String categoryName) {
		return bookService.getBooksByCategory(categoryName);
	}

	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Long id, @RequestBody BookWSData book) {
		return bookService.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}

}
