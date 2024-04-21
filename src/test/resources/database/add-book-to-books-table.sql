INSERT INTO books (id, title, author, isbn, price)
VALUES (1, 'White Fang', 'Jack London', '12587946831871', 299);

INSERT INTO categories (id, name, description)
VALUES (1, 'Detective', 'Adventures of detectives');

INSERT INTO book_category (book_id, category_id) VALUES (1, 1);