package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 블로그 ID로 게시물 찾기
    List<Post> findByBlogId(Long blogId);

    // 특정 카테고리의 게시물 찾기
    List<Post> findByCategoryId(Long categoryId);
    
    // 키워드를 포함하는 게시물 찾기
    List<Post> findByTitleContaining(String keyword);
}