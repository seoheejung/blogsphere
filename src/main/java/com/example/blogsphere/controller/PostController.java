package com.example.blogsphere.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.model.Post;
import com.example.blogsphere.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // 글 생성 및 수정
    @PostMapping
    public ResponseEntity<Post> createOrUpdatePost(@RequestBody Post post) {
        Post savedPost = postService.savePost(post);
        return ResponseEntity.ok(savedPost);
    }

    // 글 ID로 글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    // 페이징 처리된 글 목록 조회
    @GetMapping("/page")
    public ResponseEntity<List<Post>> getPostsByPage(@RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(postService.findPostsByPage(offset, limit));
    }

    // 글 상세 정보 조회
    @GetMapping("/details/{postId}")
    public ResponseEntity<Post> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPostDetailById(postId));
    }

    // 사용자 ID로 글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.findByUserIdContaining(userId);
        return ResponseEntity.ok(posts);
    }

    // 카테고리 ID로 글 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Post>> findByCategory(@PathVariable Long categoryId) {
        List<Post> posts = postService.findByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 제목으로 글 검색
    @GetMapping("/search")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam("keyword") String keyword) {
        List<Post> posts = postService.findByTitle(keyword);
        return ResponseEntity.ok(posts);
    }
}
