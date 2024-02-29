package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.BookDto;
import com.example.bookapp.src.dto.CreateBookRequestDto;
import com.example.bookapp.src.exception.EntityNotFoundException;
import com.example.bookapp.src.mapper.BookMapper;
import com.example.bookapp.src.model.Book;
import com.example.bookapp.src.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book by id" + id)
        );
        return bookMapper.toDto(book);
    }
}
