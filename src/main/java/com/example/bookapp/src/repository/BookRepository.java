package com.example.bookapp.src.repository;

import com.example.bookapp.src.model.Book;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
    Book save(Book book);

    List<Book> getAllBooks();
}
