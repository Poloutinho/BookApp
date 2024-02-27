package com.example.BookApp.service;

import com.example.BookApp.model.Book;
import com.example.BookApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
