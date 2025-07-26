package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.model.Book;
import com.library.model.Category;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	List<Book> findByAuthor(String author);

	List<Book> findByCategory(Category category);

	Optional<Book> findByIsbn(String isbn);
}
