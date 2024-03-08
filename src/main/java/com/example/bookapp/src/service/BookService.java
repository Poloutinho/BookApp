package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.BookDto;
import com.example.bookapp.src.dto.BookSearchParameters;
import com.example.bookapp.src.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAllBooks();

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto book);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
