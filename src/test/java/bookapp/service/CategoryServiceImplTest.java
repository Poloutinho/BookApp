package bookapp.service;

import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;
import bookapp.exception.EntityNotFoundException;
import bookapp.mapper.CategoryMapper;
import bookapp.model.Category;
import bookapp.repository.category.CategoryRepository;
import bookapp.service.impl.CategoryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    private static final Long ID = 1L;
    private Category category;
    @Mock
    private CategoryRepository categoryRepository;
    @Spy
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        category = new Category(ID);
        category.setName("Comedy");
        category.setDescription("Makes happy");
    }

    @Test
    @DisplayName("Save book with valid data")
    void saveBook_ValidData_Success() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();

        CategoryDto expected = createCategoryDto();

        Mockito.when(categoryMapper.toModel(requestDto)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto actual = categoryService.save(requestDto);

        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("Find all books in database")
    void findAllBooks_ValidPageable_Success() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        List<CategoryDto> expected = new ArrayList<>();
        expected.add(createCategoryDto());

        Page<Category> categoryPage = new PageImpl<>(categories);

        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(category))
                .thenReturn(createCategoryDto());

        List<CategoryDto> actual = categoryService.findAll(Mockito.mock(Pageable.class));
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Check for an exception if the book id is invalid")
    void findBookById_InvalidId_Failed() {
        Mockito.when(categoryRepository.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(ID));
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Find book by valid id")
    void findBookById_ValidId_Success() {
        Mockito.when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        CategoryDto expected = createCategoryDto();
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);
        CategoryDto actual = categoryService.getById(ID);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Update book with valid id and data")
    void updateBook_ValidIdAndData_Success() {
        Category newCategory = category;
        newCategory.setDescription("Funny");

        CategoryDto categoryDto = createCategoryDto();
        categoryDto.setDescription("Makes happy");

        CategoryDto expected = createCategoryDto();
        expected.setDescription(newCategory.getDescription());

        Mockito.when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(newCategory)).thenReturn(expected);
        Mockito.when(categoryRepository.save(newCategory)).thenReturn(newCategory);

        CategoryDto actual = categoryService.update(ID, categoryDto);

        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ID);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(newCategory);
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Comedy");
        requestDto.setDescription("Makes happy");
        return requestDto;
    }

    private CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ID);
        categoryDto.setName("Comedy");
        categoryDto.setDescription("Makes happy");
        return categoryDto;
    }
}
