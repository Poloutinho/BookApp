package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.BookDto;
import com.example.bookapp.src.dto.CreateBookRequestDto;
import com.example.bookapp.src.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
