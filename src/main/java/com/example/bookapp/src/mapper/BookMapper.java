package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.book.BookDto;
import com.example.bookapp.src.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookapp.src.dto.book.CreateBookRequestDto;
import com.example.bookapp.src.model.Book;
import com.example.bookapp.src.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(source = "categoryIds", target = "categories", qualifiedByName = "categoryById")
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(Book book, @MappingTarget BookDto bookDto) {
        Set<Category> categories = book.getCategories();
        Set<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
