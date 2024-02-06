package com.example.blogsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.model.Category;
import com.example.blogsphere.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 생성 및 수정
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // 카테고리 조회
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 카테고리 조회
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategorys();
        return ResponseEntity.ok(categories);
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    // 특정 사용자의 모든 카테고리 찾기
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByUserId(@PathVariable Long userId) {
        List<Category> categories = categoryService.findByUserIdContaining(userId);
        return ResponseEntity.ok(categories);
    }

    // 부모 카테고리 ID로 하위 카테고리 찾기
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Category>> getCategoriesByParentId(@PathVariable Long parentId) {
        List<Category> categories = categoryService.findCategoryByParentId(parentId);
        return ResponseEntity.ok(categories);
    }

    // 특정 사용자의 카테고리 별 포스트 수, 댓글 수를 조회
    @GetMapping("/statistics/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getCategoryStatistics(@PathVariable Long userId) {
        List<Map<String, Object>> statistics = categoryService.getCategoryStatisticsByUserId(userId);
        return ResponseEntity.ok(statistics);
    }

    // 사용자가 입력한 키워드에 따라 카테고리를 검색
    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam String keyword) {
        List<Category> categories = categoryService.searchCategoriesByKeyword(keyword);
        return ResponseEntity.ok(categories);
    }

    // 계층적인 카테고리 구조를 조회
    @GetMapping("/hierarchical/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getHierarchicalCategories(@PathVariable Long userId) {
        List<Map<String, Object>> categories = categoryService.getHierarchicalCategories(userId);
        return ResponseEntity.ok(categories);
    }
}