package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.book.BookDto;
import com.example.bookapp.src.dto.book.BookSearchParameters;
import com.example.bookapp.src.dto.book.CreateBookRequestDto;
import com.example.bookapp.src.exception.EntityNotFoundException;
import com.example.bookapp.src.mapper.BookMapper;
import com.example.bookapp.src.model.Book;
import com.example.bookapp.src.repository.book.BookRepository;
import com.example.bookapp.src.repository.book.BookSpecificationBuilder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> findAllByCategoryId(Long categoryId) {
        List<Book> booksByCategoryId = new ArrayList<>();
        for (Book book : bookRepository.findAllByCategoriesId(categoryId)) {
            booksByCategoryId.add(book);
        }
        return booksByCategoryId
                .stream()
                .map(book -> bookMapper.toDto(book))
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book by id" + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, BookDto updatedBookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.setTitle(updatedBookDto.getTitle());
        book.setAuthor(updatedBookDto.getAuthor());
        book.setIsbn(updatedBookDto.getIsbn());
        book.setPrice(updatedBookDto.getPrice());

        bookRepository.save(book);

        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
