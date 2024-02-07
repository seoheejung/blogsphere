package com.example.blogsphere.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.example.blogsphere.model.Post;

@Mapper
public interface BlogMapper {

    // 총 게시물 수, 총 댓글 수 등 블로그 통계 조회
    Map<String, Object> getBlogStatistics(Long blogId);

    // 특정 단어를 포함한 블로그 게시물 조회
    List<Post> searchPostsByKeyword(Long blogId, String keyword);

    // 특정 카테고리 또는 태그를 가진 게시물 검색
    List<Post> findPostsByCategoryOrTag(Long blogId, Long categoryId, String tag);

}