package com.example.blogsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.model.Blog;
import com.example.blogsphere.model.Post;
import com.example.blogsphere.service.BlogService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // 블로그 생성 및 수정
    @PostMapping
    public ResponseEntity<Blog> saveBlog(@RequestBody Blog blog) {
        Blog savedBlog = blogService.saveBlog(blog);
        return ResponseEntity.ok(savedBlog);
    }

    // 특정 블로그 조회
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 블로그 조회
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    // 블로그 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }

    // 사용자 ID로 블로그 검색
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Blog>> findByUserId(@PathVariable Long userId) {
        List<Blog> blogs = blogService.findByUserIdContaining(userId);
        return ResponseEntity.ok(blogs);
    }

    // 닉네임으로 블로그 검색
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<List<Blog>> findByNickname(@PathVariable String nickname) {
        List<Blog> blogs = blogService.findByNickname(nickname);
        return ResponseEntity.ok(blogs);
    }

    // URL로 블로그 검색
    @GetMapping("/url/{url}")
    public ResponseEntity<List<Blog>> findByUrl(@PathVariable String url) {
        List<Blog> blogs = blogService.findByUrl(url);
        return ResponseEntity.ok(blogs);
    }

    // 블로그 통계 조회
    @GetMapping("/{id}/statistics")
    public ResponseEntity<Map<String, Object>> getBlogStatistics(@PathVariable Long id) {
        Map<String, Object> statistics = blogService.getBlogStatistics(id);
        return ResponseEntity.ok(statistics);
    }

    // 특정 단어를 포함한 블로그 게시물 검색
    @GetMapping("/{id}/search")
    public ResponseEntity<List<Post>> searchPostsByKeyword(@PathVariable Long id, @RequestParam String keyword) {
        List<Post> posts = blogService.searchPostsByKeyword(id, keyword);
        return ResponseEntity.ok(posts);
    }

    // 카테고리 또는 태그로 게시물 검색
    @GetMapping("/{id}/categoryOrTag")
    public ResponseEntity<List<Post>> findPostsByCategoryOrTag(@PathVariable Long id, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) String tag) {
        List<Post> posts = blogService.findPostsByCategoryOrTag(id, categoryId, tag);
        return ResponseEntity.ok(posts);
    }
}