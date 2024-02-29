package com.example.bookapp.src.repository;

import com.example.bookapp.src.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> getAllBooks();
}
