package bookapp.service.impl;

import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;
import bookapp.exception.EntityNotFoundException;
import bookapp.mapper.CategoryMapper;
import bookapp.model.Category;
import bookapp.repository.category.CategoryRepository;
import bookapp.service.CategoryService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
