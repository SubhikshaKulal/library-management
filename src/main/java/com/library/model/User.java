package com.library.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "library")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@ManyToMany
	@JoinTable(name = "user_read_books",schema = "library" , joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<Book> readBooks = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "user_reading_list",schema = "library", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<Book> readingList = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Book> getReadBooks() {
		return readBooks;
	}

	public void setReadBooks(Set<Book> readBooks) {
		this.readBooks = readBooks;
	}

	public Set<Book> getReadingList() {
		return readingList;
	}

	public void setReadingList(Set<Book> readingList) {
		this.readingList = readingList;
	}

}
