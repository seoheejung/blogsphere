package com.example.blogsphere.service;

import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.example.blogsphere.model.Category;
import com.example.blogsphere.repository.jpa.CategoryRepository;
import com.example.blogsphere.repository.mybatis.CategoryMapper;

import jakarta.validation.constraints.NotNull;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    
    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public Category saveCategory(@NotNull Category category) {
        // 카테고리 생성 및 수정 로직
        return categoryRepository.save(category);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(@NotNull Long id) {
        // 카테고리 조회 로직
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategorys() {
        // 모든 카테고리 조회 로직
        return categoryRepository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public void deleteCategory(@NotNull Long id) {
        // 카테고리 삭제 로직
        categoryRepository.deleteById(id);
    }

    // JPA를 사용한 카테고리 조회
    @Transactional(readOnly = true)
    public List<Category> findByUserIdContaining(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Category> findCategoryByParentId(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }


    // MyBatis를 사용한 카테고리 조회 기능
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getCategoryStatisticsByUserId(Long userId) {
        return categoryMapper.getCategoryStatisticsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Category> searchCategoriesByKeyword(String keyword) {
        return categoryMapper.searchCategoriesByKeyword(keyword);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHierarchicalCategories(Long userId) {
        return categoryMapper.getHierarchicalCategories(userId);
    }
}
