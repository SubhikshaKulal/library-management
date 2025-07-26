# library-management
Library Management System

-----Spring Boot Application Main---
LibraryManagementApplication.java

-----------------------------------------
---DB Creation----(Used Postgresql)
CREATE DATABASE library_db;

-- 1. Create schema
CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE library.categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Create sequence
CREATE SEQUENCE IF NOT EXISTS library.book_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



-- 3. Create books table (no category column)
CREATE TABLE IF NOT EXISTS library.books (
    id BIGINT PRIMARY KEY DEFAULT nextval('library.book_sequence'),
    isbn VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
publication_date DATE,
    category_id BIGINT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES library.categories(id)
);

---------------------------

CREATE SEQUENCE IF NOT EXISTS library.users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
-- DROP TABLE IF EXISTS library.users;

CREATE TABLE IF NOT EXISTS library.users
(
    id bigint NOT NULL DEFAULT nextval('library.users_id_seq'::regclass),
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
	
commit;
--------------------------------------

----Insert Script-----

----Inserting Data to Categories Table----------

INSERT INTO library.categories (id, name) VALUES
(1, 'Fiction'),
(2, 'Non-Fiction'),
(3, 'Programming'),
(4, 'Science'),
(5, 'Philosophy'),
(6, 'Biography');

------------Inserting Data to Books Tables For Testing purpose------------------


INSERT INTO library.books(isbn, title, author, publication_date, category_id) VALUES
('9788172234980', 'The White Tiger', 'Aravind Adiga', '2008-10-01', 1),             -- Fiction
('9780143422297', 'The Secret of the Nagas', 'Amish Tripathi', '2011-08-12', 1),    -- Fiction
('9789386224845', 'Why I am a Hindu', 'Shashi Tharoor', '2018-01-26', 5),           -- Philosophy
('9780670088319', 'Indira: India’s Most Powerful Prime Minister', 'Sagarika Ghose', '2017-07-01', 6), -- Biography
('9780143422298', 'The Immortals of Meluha', 'Amish Tripathi', '2010-02-15', 1),    -- Fiction
('9780143429365', 'India After Gandhi', 'Ramachandra Guha', '2007-08-30', 2),       -- Non-Fiction
('9780143418994', 'The Great Indian Novel', 'Shashi Tharoor', '1989-01-01', 1),     -- Fiction
('9780143440056', 'I Do What I Do', 'Raghuram Rajan', '2017-09-05', 2),             -- Non-Fiction
('9788192910961', 'Revolution 2020', 'Chetan Bhagat', '2011-10-01', 1),             -- Fiction
('9789353029955', 'Let Us Java', 'Yashavant Kanetkar', '2019-06-01', 3);            -- Programming

--------------------------------

---Inserting Data to User Table----

INSERT INTO library.users (username) VALUES ('Subhiksha');
INSERT INTO library.users (username) VALUES ('Karthik');

-----------------------------------------------------------------------------------------

----Postman Request for the APIs--------------

1.AddBook
	Method: POST
	END Point: http://localhost:8080/api/books
	Request Body: 
			{
			  "title": "Clean Code",
			  "author": "Robert C. Martin",
			  "isbn": "9780132350884",
			  "categoryName": "Technology"
			}
			
2. UpdateBook
	Method: POST
	END Point: http://localhost:8080/api/books/1
	Request Body:
			{
			  "title": "Clean Code (Updated)",
			  "author": "Robert C. Martin",
			  "isbn": "9780132350884",
			  "categoryName": "Technology"
			}
			
3. GetAllBooks
	Method: GET
	END Point: http://localhost:8080/api/books
	

4. GetBookByAuthor
	Method: GET
	END Point: http://localhost:8080/api/books/author/Robert C. Martin
	
	
5. GetBookByCategory
	Method: GET
	END Point: http://localhost:8080/api/books/category/Technology
	

6. DeleteBook
	Method: DELETE
	END Point: http://localhost:8080/api/books/1
	
	
7. GetRecommendedBooks
	Method: GET
	END Point: http://localhost:8080/users/1/recommendations
	

8. AddBookToReadingList
	Method: POST
	END Point: http://localhost:8080/users/1/reading-list/isbn/9780132350884
	
	
9. MarkBookAsRead
	Method: POST
	END Point: http://localhost:8080/users/1/read/isbn/9788172234980
	

