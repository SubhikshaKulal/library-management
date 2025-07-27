package com.library.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exception.LibraryServiceNoDataFoundException;
import com.library.model.Book;
import com.library.model.Category;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

@Service
public class RecommendationService {
	private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	public void markBookAsRead(Long userId, String isbn) {
		logger.info("Entering RecommendationService.markBookAsRead");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("User Not Found", "User Lookup Failed",
						"User with ID " + userId + " was not found in the system."));

		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("Book Not Found", "Book Lookup Failed",
						"Book with ISBN " + isbn + " was not found in the system."));

		user.getReadBooks().add(book);
		userRepository.save(user);
		logger.info("Exiting RecommendationService.markBookAsRead");
	}

	public void addBookToReadingList(Long userId, String isbn) {
		logger.info("Entering RecommendationService.addBookToReadingList");
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("User Not Found", "User Lookup Failed",
						"User with ID " + userId + " was not found in the system."));

		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("Book Not Found", "Book Lookup Failed",
						"Book with ISBN " + isbn + " was not found in the system."));

		user.getReadingList().add(book);
		userRepository.save(user);
		
		logger.info("Exiting RecommendationService.addBookToReadingList");
	}

	public List<Book> getRecommendations(Long userId) {
		logger.info("Entering RecommendationService.getRecommendations");
		User user = getUserById(userId);
		Set<Book> readBooks = user.getReadBooks();
		Set<Book> readingList = user.getReadingList();

		// Count categories read
		Map<Category, Long> categoryFrequency = readBooks.stream()
				.collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));

		// Get top category IDs
		List<Category> topCategories = categoryFrequency.entrySet().stream()
				.sorted((a, b) -> Long.compare(b.getValue(), a.getValue())).map(Map.Entry::getKey).limit(3).toList();

		Set<Long> excludeBookIds = Stream.concat(readBooks.stream(), readingList.stream()).map(Book::getId)
				.collect(Collectors.toSet());

		// Fetch books not read yet and from top categories
		return bookRepository.findAll().stream().filter(book -> topCategories.contains(book.getCategory()))
				.filter(book -> !excludeBookIds.contains(book.getId())).limit(10).toList();
	}

	private User getUserById(Long id) {
		logger.info("Entering RecommendationService.getUserById");
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

}
