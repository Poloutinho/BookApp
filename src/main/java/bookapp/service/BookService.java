package bookapp.service;

import bookapp.dto.book.BookDto;
import bookapp.dto.book.BookSearchParameters;
import bookapp.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto book);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> findAllByCategoryId(Long categoryId);
}
