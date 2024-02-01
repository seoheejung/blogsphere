package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.Blog;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // 사용자 ID로 블로그 찾기
    Optional<Blog> findByUserId(Long userId);

    // 블로그 URL로 블로그 찾기
    Optional<Blog> findByUrl(String url);
}