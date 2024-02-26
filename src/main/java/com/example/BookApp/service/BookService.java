package com.example.BookApp.service;

import com.example.BookApp.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book save(Book book);
    List<Book> getAll();
}
