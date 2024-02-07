package com.example.blogsphere.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.example.blogsphere.model.Comment;

@Mapper
public interface CommentMapper {
    // 특정 게시물 또는 블로그에 대한 최신 댓글을 조회
    List<Comment> findLatestCommentsByPostId(@Param("postId") Long postId, @Param("limit") int limit);

    // 대댓글을 포함한 계층적인 댓글 구조를 조회
    List<Comment> findNestedCommentsByPostId(@Param("postId") Long postId);

    // 특정 사용자가 작성한 댓글들을 조회
    List<Comment> findCommentsByUserId(@Param("userId") Long userId);

    // 댓글 내용에 특정 키워드가 포함된 댓글을 찾는 기능
    List<Comment> searchCommentsByKeyword(@Param("keyword") String keyword);
}
