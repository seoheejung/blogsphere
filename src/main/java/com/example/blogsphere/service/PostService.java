package com.example.blogsphere.service;

import org.springframework.stereotype.Service;

import com.example.blogsphere.model.Post;
import com.example.blogsphere.repository.jpa.PostRepository;
import com.example.blogsphere.repository.mybatis.PostMapper;

import jakarta.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }
    
    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public Post savePost(@NotNull Post post) {
        // 글 생성 및 수정 로직
        return postRepository.save(post);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Optional<Post> getPostById(@NotNull Long id) {
        // 글 조회 로직
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        // 모든 글 조회 로직
        return postRepository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public void deletePost(@NotNull Long id) {
        // 글 삭제 로직
        postRepository.deleteById(id);
    }

    // JPA를 사용한 글 조회
    @Transactional(readOnly = true)
    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Post> findByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Post> findByTitle(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }

    // MyBatis를 사용한 글 조회 기능
    @Transactional(readOnly = true)
    public List<Post> findPostsByPage(Long blogId, int offset, int limit) {
        return postMapper.findPostsByPage(blogId, offset, limit);
    }

    @Transactional(readOnly = true)
    public Post findPostDetailById(Long postId) {
        return postMapper.findPostDetailById(postId);
    }
}