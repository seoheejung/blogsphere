package com.example.blogsphere.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogsphere.model.Comment;
import com.example.blogsphere.repository.jpa.CommentRepository;
import com.example.blogsphere.repository.mybatis.CommentMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public Comment saveComment(@NotNull Comment comment) {
        // 댓글 생성 및 수정 로직
        return commentRepository.save(comment);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(@NotNull Long id) {
        // 댓글 조회 로직
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        // 모든 댓글 조회 로직
        return commentRepository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public void deleteComment(@NotNull Long id) {
        // 댓글 삭제 로직
        commentRepository.deleteById(id);
    }

    // JPA를 사용한 블로그 조회
    @Transactional(readOnly = true)
    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Comment> findByParentId(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }

    // MyBatis를 사용한 글 조회 기능
    @Transactional(readOnly = true)
    public List<Comment> findLatestCommentsByPostId(Long postId, int limit) {
        return commentMapper.findLatestCommentsByPostId(postId, limit);
    }

    @Transactional(readOnly = true)
    public List<Comment> findNestedCommentsByPostId(Long postId) {
        return commentMapper.findNestedCommentsByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Comment> findCommentsByUserId(Long userId) {
        return commentMapper.findCommentsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Comment> searchCommentsByKeyword(String keyword) {
        return commentMapper.searchCommentsByKeyword(keyword);
    }
}
