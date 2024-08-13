package ru.scadouge.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.category.dto.CategoryDto;
import ru.scadouge.ewm.category.mapper.CategoryMapper;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.category.service.CategoryService;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class CategoryPublicController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("PUBLIC: Получение списка категорий from={}, size={}", from, size);
        List<Category> categories = categoryService.getCategories(from, size);
        return categoryMapper.toCategoryDto(categories);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        log.info("PUBLIC: Получение категории catId={}", catId);
        Category category = categoryService.getCategory(catId);
        return categoryMapper.toCategoryDto(category);
    }
}
