package com.example.blogsphere.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.CommonFunction;
import com.example.blogsphere.model.Category;
import com.example.blogsphere.model.ResultMessage;
import com.example.blogsphere.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 생성 및 수정
    @PostMapping
    public ResponseEntity<ResultMessage> createCategory(@RequestBody Category category) {
        logger.info(CommonFunction.getClassName());
        try {
            Category savedCategory = categoryService.saveCategory(category);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("Category created/updated successfully")
                                                .result(savedCategory)
                                                .build());
        } catch (Exception e) {
            logger.error("createCategory Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 카테고리 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage> getCategoryById(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            return categoryService.getCategoryById(id)
                    .map(category -> ResponseEntity.ok(ResultMessage.builder()
                                                                .code(200)
                                                                .msg("success")
                                                                .result(category)
                                                                .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ResultMessage.builder()
                                                            .code(404)
                                                            .msg("Category not found")
                                                            .result("F")
                                                            .build()));
        } catch (Exception e) {
            logger.error("getCategoryById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 모든 카테고리 조회
    @GetMapping
    public ResponseEntity<ResultMessage> getAllCategories() {
        logger.info(CommonFunction.getClassName());
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(categories)
                                                .build());
        } catch (Exception e) {
            logger.error("getAllCategorys Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage> deleteCategory(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("Category deleted successfully")
                                                .result("Success")
                                                .build());
        } catch (Exception e) {
            logger.error("deleteCategory Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }
    // 특정 사용자의 모든 카테고리 찾기
    @PostMapping("/user")
    public ResponseEntity<ResultMessage> getCategoriesByUserId(@RequestParam Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Category> categories = categoryService.findByUserIdContaining(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(categories)
                                                .build());
        } catch (Exception e) {
            logger.error("getCategoriesByUserId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 부모 카테고리 ID로 하위 카테고리 찾기
    @PostMapping("/parent")
    public ResponseEntity<ResultMessage> getCategoriesByParentId(@RequestParam Long parentId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Category> categories = categoryService.findCategoryByParentId(parentId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(categories)
                                                .build());
        } catch (Exception e) {
            logger.error("getCategoriesByParentId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 사용자의 카테고리 별 포스트 수, 댓글 수를 조회
    @PostMapping("/statistics")
    public ResponseEntity<ResultMessage> getCategoryStatistics(@RequestParam Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Map<String, Object>> statistics = categoryService.getCategoryStatisticsByUserId(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(statistics)
                                                .build());
        } catch (Exception e) {
            logger.error("getCategoryStatistics Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 사용자가 입력한 키워드에 따라 카테고리를 검색
    @PostMapping("/search")
    public ResponseEntity<ResultMessage> searchCategories(@RequestParam String keyword) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Category> categories = categoryService.searchCategoriesByKeyword(keyword);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(categories)
                                                .build());
        } catch (Exception e) {
            logger.error("searchCategories Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 계층적인 카테고리 구조를 조회
    @GetMapping("/hierarchical/{userId}")
    public ResponseEntity<ResultMessage> getHierarchicalCategories(@PathVariable Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Map<String, Object>> categories = categoryService.getHierarchicalCategories(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(categories)
                                                .build());
        } catch (Exception e) {
            logger.error("getHierarchicalCategories Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

}