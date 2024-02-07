package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // 사용자 ID로 블로그 찾기
    List<Blog> findByUserId(Long userId);

    // 사용자 닉네임로 블로그 찾기
    List<Blog> findByNickname(String nickname);

    // 블로그 URL로 블로그 찾기
    List<Blog> findByUrl(String url);
}