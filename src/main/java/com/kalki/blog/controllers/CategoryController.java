package com.kalki.blog.controllers;

import com.kalki.blog.config.MessageConstants;
import com.kalki.blog.payloads.ApiResponseWrapper;
import com.kalki.blog.payloads.CategoryDto;
import com.kalki.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseWrapper<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto created = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(ApiResponseWrapper.success(MessageConstants.CATEGORY_CREATED, created), HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<ApiResponseWrapper<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId) {
        CategoryDto updated = categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.CATEGORY_UPDATED, updated));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteCategory(@PathVariable Integer catId) {
        categoryService.deleteCategory(catId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.CATEGORY_DELETED, null));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<ApiResponseWrapper<CategoryDto>> getCategory(@PathVariable Integer catId) {
        CategoryDto categoryDto = categoryService.getCategory(catId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.CATEGORY_FETCHED, categoryDto));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponseWrapper<List<CategoryDto>>> getCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.CATEGORIES_FETCHED, categories));
    }
}
