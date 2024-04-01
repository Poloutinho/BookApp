package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.category.CategoryDto;
import com.example.bookapp.src.dto.category.CreateCategoryRequestDto;
import com.example.bookapp.src.model.Category;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);

    @Named("categoryById")
    default Category categoryById(Long id) {
        return Optional.ofNullable(id)
                .map(Category::new)
                .orElse(null);
    }
}
