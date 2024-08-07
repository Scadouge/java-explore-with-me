package ru.scadouge.ewm.category.mapper;

import org.mapstruct.*;
import ru.scadouge.ewm.category.dto.CategoryDto;
import ru.scadouge.ewm.category.dto.NewCategoryDto;
import ru.scadouge.ewm.category.model.Category;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toModel(NewCategoryDto newCategoryDto);

    Category toModel(CategoryDto categoryDto);

    List<CategoryDto> toCategoryDto(List<Category> categories);

    CategoryDto toCategoryDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Category category, Category updated);
}
