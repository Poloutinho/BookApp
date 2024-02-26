package com.example.BookApp.repository;

import com.example.BookApp.model.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);
    List<Book> findAll();
}
