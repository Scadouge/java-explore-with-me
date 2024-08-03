package ru.scadouge.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.category.dto.CategoryDto;
import ru.scadouge.ewm.category.mapper.CategoryMapper;
import ru.scadouge.ewm.category.dto.NewCategoryDto;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("ADMIN: Создание новой категории newCategoryDto={}", newCategoryDto);
        Category category = categoryService.createCategory(categoryMapper.toModel(newCategoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable long catId,
                                     @Valid @RequestBody CategoryDto categoryDto) {
        log.info("ADMIN: Обновление категории catId={}, categoryDto={}", catId, categoryDto);
        Category category = categoryService.updateCategory(catId, categoryMapper.toModel(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("ADMIN: Удаление категории catId={}", catId);
        categoryService.deleteCategory(catId);
    }
}
