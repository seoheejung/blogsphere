package com.example.blogsphere.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.blogsphere.model.Post;

@Mapper
public interface PostMapper {

    // 페이징 처리를 위한 글 목록 조회
    List<Post> findPostsByPage(@Param("offset") int offset, @Param("limit") int limit);

    // 글의 상세 정보와 관련 데이터(예: 댓글, 카테고리) 함께 조회
    Post findPostDetailById(@Param("postId") Long postId);
}