package com.example.blogsphere.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.example.blogsphere.model.Blog;
import com.example.blogsphere.model.Post;
import com.example.blogsphere.repository.jpa.BlogRepository;
import com.example.blogsphere.repository.mybatis.BlogMapper;

import jakarta.validation.constraints.NotNull;

public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }
    
    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public Blog saveBlog(@NotNull Blog blog) {
        // 블로그 생성 및 수정 로직
        return blogRepository.save(blog);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Optional<Blog> getBlogById(@NotNull Long id) {
        // 블로그 조회 로직
        return blogRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Blog> getAllBlogs() {
        // 모든 블로그 조회 로직
        return blogRepository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public void deleteBlog(@NotNull Long id) {
        // 블로그 삭제 로직
        blogRepository.deleteById(id);
    }

    // JPA를 사용한 블로그 조회
    @Transactional(readOnly = true)
    public List<Blog> findByUserIdContaining(Long userId) {
        return blogRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Blog> findByNickname(String nickname) {
        return blogRepository.findByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public List<Blog> findByUrl(String url) {
        return blogRepository.findByUrl(url);
    }

    // MyBatis를 사용한 글 조회 기능
    @Transactional(readOnly = true)
    public Map<String, Object> getBlogStatistics(Long blogId) {
        return blogMapper.getBlogStatistics(blogId);
    }

    @Transactional(readOnly = true)
    public List<Post> searchPostsByKeyword(Long blogId, String keyword) {
        return blogMapper.searchPostsByKeyword(blogId, keyword);
    }

    @Transactional(readOnly = true)
    public List<Post> findPostsByCategoryOrTag(Long blogId, Long categoryId, String tag) {
        return blogMapper.findPostsByCategoryOrTag(blogId, categoryId, tag);
    }
}
