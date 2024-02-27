package com.example.BookApp.service;

import com.example.BookApp.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    List<Book> getAllBooks();
}
