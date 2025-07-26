package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Book;
import com.library.service.RecommendationService;

@RestController
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;

	@PostMapping("/users/{userId}/read/isbn/{isbn}")
	public ResponseEntity<?> markBookAsRead(@PathVariable Long userId, @PathVariable String isbn) {
		recommendationService.markBookAsRead(userId, isbn);
		return ResponseEntity.ok("Book marked as read for user.");
	}

	@PostMapping("/users/{userId}/reading-list/isbn/{isbn}")
	public ResponseEntity<?> addToReadingList(@PathVariable Long userId, @PathVariable String isbn) {
		recommendationService.addBookToReadingList(userId, isbn);
		return ResponseEntity.ok("Book added to reading list.");
	}

	@GetMapping("/users/{userId}/recommendations")
	public ResponseEntity<List<Book>> getRecommendations(@PathVariable Long userId) {
		return ResponseEntity.ok(recommendationService.getRecommendations(userId));
	}
}
