package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.book.BookDto;
import com.example.bookapp.src.dto.book.BookSearchParameters;
import com.example.bookapp.src.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto book);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDto> findAll(Pageable pageable);
}
