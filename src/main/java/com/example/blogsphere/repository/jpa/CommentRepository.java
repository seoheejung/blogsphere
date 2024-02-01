package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시물의 모든 댓글 찾기
    List<Comment> findByPostId(Long postId);

    // 부모 댓글 ID로 대댓글 찾기
    List<Comment> findByParentId(Long parentId);
}
