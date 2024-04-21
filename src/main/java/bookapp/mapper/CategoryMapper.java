package bookapp.mapper;

import bookapp.config.MapperConfig;
import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;
import bookapp.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);
}
