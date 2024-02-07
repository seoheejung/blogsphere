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

import com.example.blogsphere.model.Comment;
import com.example.blogsphere.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성 및 수정
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    // 특정 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    // 특정 게시물의 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findByPostId(postId));
    }

    // 특정 부모 댓글에 대한 대댓글 조회
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Comment>> findByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(commentService.findByParentId(parentId));
    }

    // 특정 게시물의 최신 댓글 조회
    @GetMapping("/latest/{postId}")
    public ResponseEntity<List<Comment>> findLatestCommentsByPostId(@PathVariable Long postId, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(commentService.findLatestCommentsByPostId(postId, limit));
    }

    // 특정 게시물의 계층적 댓글 조회
    @GetMapping("/nested/{postId}")
    public ResponseEntity<List<Comment>> findNestedCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findNestedCommentsByPostId(postId));
    }

    // 특정 사용자가 작성한 댓글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> findCommentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.findCommentsByUserId(userId));
    }

    // 키워드를 포함하는 댓글 검색
    @GetMapping("/search")
    public ResponseEntity<List<Comment>> searchCommentsByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(commentService.searchCommentsByKeyword(keyword));
    }
}

