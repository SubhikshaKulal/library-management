package com.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.library.exception.LibraryServiceNoDataFoundException;
import com.library.model.Book;
import com.library.model.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.ws.request.BookWSData;

@Service
public class BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	private BookRepository bookRepo;
	private CategoryRepository categoryRepository;

	// Constructor Injection
	public BookService(BookRepository mBookRepository, CategoryRepository mCategoryRepository) {
		bookRepo = mBookRepository;
		categoryRepository = mCategoryRepository;
	}

	public Book addBook(BookWSData bookData) {
		logger.info("Entering BookService.addBook");

		Category category = categoryRepository.findByName(bookData.getCategoryName())
				.orElseThrow(() -> new LibraryServiceNoDataFoundException(
						"Category not found: " + bookData.getCategoryName(), "Invalid Category Name",
						"The provided category name does not exist in the system. Please verify and try again."));

		if (bookRepo.findByIsbn(bookData.getIsbn()).isPresent()) {
			throw new LibraryServiceNoDataFoundException("ISBN Validation Failed: " + bookData.getIsbn(),
					"Already Exists", "The provided book exists in the system. Please retry adding other books.");
		}

		Book book = new Book();
		book.setTitle(bookData.getTitle());
		book.setAuthor(bookData.getAuthor());
		book.setIsbn(bookData.getIsbn());
		book.setCategory(category);

		logger.info("Exiting BookService.addBook");
		return bookRepo.save(book);
	}

	public List<Book> getAllBooks() {
		logger.info("Entering BookService.getAllBooks");

		List<Book> bookList = bookRepo.findAll();

		if (bookList.isEmpty()) {
			throw new LibraryServiceNoDataFoundException("Books Not Found", "Empty Book List",
					"There are no books stored in the system at this moment.");
		}
		logger.info("Exiting BookService.getAllBooks");
		return bookList;
	}

	public List<Book> getBooksByAuthor(String author) {
		logger.info("Entering BookService.getBooksByAuthor");
		List<Book> books = bookRepo.findByAuthor(author);
		if (books.isEmpty()) {
			throw new LibraryServiceNoDataFoundException("No books found by author: " + author, "Books Not Found",
					"There are no books in the database written by the author: " + author);
		}
		logger.info("Exiting BookService.getBooksByAuthor");
		return books;
	}

	public List<Book> getBooksByCategory(String categoryName) {
		logger.info("Entering BookService.getBooksByCategory");

		Category category = categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("Category not found: " + categoryName,
						"Invalid Category Name", "The provided category name does not exist. Please try a valid one."));

		List<Book> books = bookRepo.findByCategory(category);
		if (books.isEmpty()) {
			throw new LibraryServiceNoDataFoundException("No books found in category: " + categoryName,
					"Books Not Found", "There are no books available under the specified category.");
		}
		logger.info("Exiting BookService.getBooksByCategory");
		return books;
	}

	public Book updateBook(Long id, BookWSData updatedBook) {
		logger.info("Entering BookService.updateBook");

		Book book = bookRepo.findById(id)
				.orElseThrow(() -> new LibraryServiceNoDataFoundException("Book not found with ID: " + id,
						"Book Not Found", "No book exists with the provided ID. Please check the ID and try again."));

		Category category = categoryRepository.findByName(updatedBook.getCategoryName())
				.orElseThrow(() -> new LibraryServiceNoDataFoundException(
						"Category not found: " + updatedBook.getCategoryName(), "Invalid Category Name",
						"The provided category name does not exist in the system. Please verify and try again."));

		book.setTitle(updatedBook.getTitle());
		book.setAuthor(updatedBook.getAuthor());
		book.setPublicationDate(updatedBook.getPublicationDate());
		book.setCategory(category);

		logger.info("Exiting BookService.updateBook");

		return bookRepo.save(book);
	}

	public void deleteBook(Long id) {
		logger.info("Entering BookService.deleteBook");

		if (!bookRepo.existsById(id)) {
			throw new LibraryServiceNoDataFoundException("Cannot delete. Book not found with ID: " + id,
					"Delete Failed", "No book exists with the given ID. Deletion cannot proceed.");
		}
		bookRepo.deleteById(id);

		logger.info("Exiting BookService.deleteBook");
	}

}
