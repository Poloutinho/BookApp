package bookapp.service;

import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CategoryDto categoryDto);

    void deleteById(Long id);
}
