package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 특정 사용자의 모든 카테고리 찾기
    List<Category> findByUserId(Long userId);

    // 부모 카테고리 ID로 하위 카테고리 찾기
    List<Category> findByParentId(Long parentId);
}
