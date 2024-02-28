package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.BookDto;
import com.example.bookapp.src.dto.CreateBookRequestDto;
import com.example.bookapp.src.mapper.BookMapper;
import com.example.bookapp.src.model.Book;
import com.example.bookapp.src.repository.BookRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
