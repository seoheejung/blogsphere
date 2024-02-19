package com.example.blogsphere.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogsphere.CommonFunction;
import com.example.blogsphere.model.Comment;
import com.example.blogsphere.model.ResultMessage;
import com.example.blogsphere.service.CommentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final CommonFunction commonFunction;

    // 댓글 생성 및 수정
    @PostMapping
    public ResponseEntity<ResultMessage> saveComment(@RequestBody Comment comment) {
        logger.info(CommonFunction.getClassName());
        try {
            Comment savedComment = commentService.saveComment(comment);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(savedComment)
                                                .build());
        } catch (Exception e) {
            logger.error("saveComment Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage> getCommentById(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            return commentService.getCommentById(id)
                    .map(comment -> ResponseEntity.ok(ResultMessage.builder()
                                                                    .code(200)
                                                                    .msg("success")
                                                                    .result(comment)
                                                                    .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                            .body(ResultMessage.builder()
                                                                .code(404)
                                                                .msg("Comment not found")
                                                                .result("F")
                                                                .build()));
        } catch (Exception e) {
            logger.error("getCommentById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 모든 댓글 조회
    @GetMapping
    public ResponseEntity<ResultMessage> getAllComments() {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.getAllComments();
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("getAllComments Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage> deleteComment(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("Comment deleted successfully")
                                                .result("Success")
                                                .build());
        } catch (Exception e) {
            logger.error("deleteComment Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 게시물의 댓글 조회
    @GetMapping("/post")
    public ResponseEntity<ResultMessage> findByPostId(@RequestParam Long postId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.findByPostId(postId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("findByPostId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 부모 댓글에 대한 대댓글 조회
    @GetMapping("/parent")
    public ResponseEntity<ResultMessage> findByParentId(@RequestParam Long parentId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.findByParentId(parentId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("findByParentId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 게시물의 최신 댓글 조회
    @GetMapping("/latest")
    public ResponseEntity<ResultMessage> findLatestCommentsByPostId(@RequestParam Long postId, @RequestParam(defaultValue = "10") int limit) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.findLatestCommentsByPostId(postId, limit);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("findLatestCommentsByPostId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 게시물의 계층적 댓글 조회
    @PostMapping("/nested")
    public ResponseEntity<ResultMessage> findNestedCommentsByPostId(@RequestParam Long postId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.findNestedCommentsByPostId(postId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("findNestedCommentsByPostId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 사용자가 작성한 댓글 조회
    @PostMapping("/userId")
    public ResponseEntity<ResultMessage> findCommentsByUserId(@RequestParam Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.findCommentsByUserId(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("findCommentsByUserId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 키워드를 포함하는 댓글 검색
    @PostMapping("/search")
    public ResponseEntity<ResultMessage> searchCommentsByKeyword(@RequestParam String keyword) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Comment> comments = commentService.searchCommentsByKeyword(keyword);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(comments)
                                                .build());
        } catch (Exception e) {
            logger.error("searchCommentsByKeyword Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

}

