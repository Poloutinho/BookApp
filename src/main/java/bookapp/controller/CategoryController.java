package bookapp.controller;

import bookapp.dto.book.BookDto;
import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;
import bookapp.service.BookService;
import bookapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories management", description = "Endpoints for managing")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Add new category", description = "Add new category")
    public CategoryDto createCategory(@RequestBody CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all categories", description = "Get all categories")
    @GetMapping
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Find category by Id", description = "Find category by Id")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update category by Id", description = "Update category by Id")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category by Id", description = "Delete category by Id")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/books")
    @Operation(summary = "Search books by categories",
            description = "Search books by categories")
    public List<BookDto> getBooksByCategoryId(@PathVariable Long id) {
        return bookService.findAllByCategoryId(id);
    }
}
