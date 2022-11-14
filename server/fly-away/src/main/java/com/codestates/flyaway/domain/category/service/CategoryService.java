package com.codestates.flyaway.domain.category.service;

import com.codestates.flyaway.domain.category.entity.Category;
import com.codestates.flyaway.domain.category.repository.CategoryRepository;
import com.codestates.flyaway.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codestates.flyaway.global.exception.ExceptionCode.*;
import static com.codestates.flyaway.web.category.dto.CategoryDto.*;
import static com.codestates.flyaway.web.category.dto.CategoryDto.CategoryResponseDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto create(CreateCategory createCategoryDto) {
        Category category = new Category(createCategoryDto.getCategoryName());
        categoryRepository.save(category);

        return toResponseDto(category);
    }

    public CategoryResponseDto update(UpdateCategory updateCategoryDto) {
        final Category category = categoryRepository.getReferenceById(updateCategoryDto.getCategoryId());
        category.update(category.getCategoryName());

        return toResponseDto(category);
    }

    public List<MultiCategoryDto> readAll(Pageable pageable) {
        Page<Category> savedCategory = categoryRepository.findAll(pageable);
        List<Category> categories = savedCategory.getContent();

        return MultiCategoryDto.toResponsesDto(categories);
    }

    public void delete(Long categoryId) {
        Category category = findById(categoryId);
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessLogicException(CATEGORY_NOT_FOUND));
    }
}
