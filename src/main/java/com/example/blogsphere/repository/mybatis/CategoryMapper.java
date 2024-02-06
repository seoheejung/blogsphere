package com.example.blogsphere.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import com.example.blogsphere.model.Category;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    // 특정 사용자의 카테고리 별 포스트 수, 댓글 수를 조회
    List<Map<String, Object>> getCategoryStatisticsByUserId(Long userId);

    // 사용자가 입력한 키워드에 따라 카테고리를 검색
    List<Category> searchCategoriesByKeyword(String keyword);

    // 계층적인 카테고리 구조를 조회
    List<Map<String, Object>> getHierarchicalCategories(Long userId);
}
