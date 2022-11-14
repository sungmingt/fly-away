package com.codestates.flyaway.web.category.controller;

import com.codestates.flyaway.domain.category.service.CategoryService;
import com.codestates.flyaway.web.category.dto.CategoryDto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.codestates.flyaway.web.category.dto.CategoryDto.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CategoryResponseDto create(@Validated @RequestBody CreateCategory createCategoryDto) {
        return categoryService.create(createCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    public CategoryResponseDto update(@PathVariable("categoryId") Long categoryId,
                                      @RequestBody UpdateCategory updateCategoryDto) {
        updateCategoryDto.setCategoryId(categoryId);
        return categoryService.update(updateCategoryDto);
    }

    @GetMapping 
    public List<MultiCategoryDto> read(Pageable pageable) {
        return categoryService.readAll(pageable);
    }

    @DeleteMapping("/{categoryId}")
    public HttpStatus delete(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return NO_CONTENT;
    }
}
