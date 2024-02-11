package com.example.blogsphere.controller;

import java.util.List;

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
import com.example.blogsphere.model.Post;
import com.example.blogsphere.model.ResultMessage;
import com.example.blogsphere.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    // 글 생성 및 수정
    @PostMapping
    public ResponseEntity<ResultMessage> createOrUpdatePost(@RequestBody Post post) {
        logger.info(CommonFunction.getClassName());
        try {
            Post savedPost = postService.savePost(post);
            ResultMessage resultMessage = ResultMessage.builder()
                                                        .code(200)
                                                        .msg("success")
                                                        .result(savedPost)
                                                        .build();
            return ResponseEntity.ok(resultMessage);
        } catch (Exception e) {
            logger.error("createOrUpdatePost Error: {}", e.getMessage());
            ResultMessage resultMessage = ResultMessage.builder()
                                                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                        .msg("Internal server error: " + e.getMessage())
                                                        .result("F")
                                                        .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
        }
    }

    // 글 ID로 글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage> getPostById(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            return postService.getPostById(id)
                    .map(post -> ResponseEntity.ok(ResultMessage.builder()
                                                                .code(200)
                                                                .msg("success")
                                                                .result(post)
                                                                .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                            .body(ResultMessage.builder()
                                                                .code(404)
                                                                .msg("Post not found")
                                                                .result("F")
                                                                .build()));
        } catch (Exception e) {
            logger.error("getPostById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 모든 글 조회
    @GetMapping
    public ResponseEntity<ResultMessage> getAllPosts() {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("getAllPosts Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage> deletePost(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            postService.deletePost(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("Post deleted successfully")
                                                .result("Success")
                                                .build());
        } catch (Exception e) {
            logger.error("deletePost Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 해당 블로그의 페이징 처리된 글 목록 조회
    @PostMapping("/page")
    public ResponseEntity<ResultMessage> getPostsByPage(@RequestParam Long blogId, @RequestParam int offset, @RequestParam int limit) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = postService.findPostsByPage(blogId, offset, limit);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("getPostsByPage Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 글 상세 정보 조회
    @PostMapping("/details")
    public ResponseEntity<ResultMessage> findPostDetailById(@RequestParam Long postId) {
        logger.info(CommonFunction.getClassName());
        try {
            Post post = postService.findPostDetailById(postId);
            if (post != null) {
                return ResponseEntity.ok(ResultMessage.builder()
                                                    .code(200)
                                                    .msg("success")
                                                    .result(post)
                                                    .build());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(ResultMessage.builder()
                                                        .code(404)
                                                        .msg("Post not found")
                                                        .result("F")
                                                        .build());
            }
        } catch (Exception e) {
            logger.error("findPostDetailById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 사용자 ID로 글 조회
    @PostMapping("/userId")
    public ResponseEntity<ResultMessage> findByUserId(@RequestParam Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = postService.findByUserId(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("findByUserId Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 카테고리 ID로 글 조회
    @PostMapping("/category")
    public ResponseEntity<ResultMessage> findByCategory(@RequestParam Long categoryId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = postService.findByCategory(categoryId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("findByCategory Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 제목으로 글 검색
    @PostMapping("/search")
    public ResponseEntity<ResultMessage> findByTitle(@RequestParam("keyword") String keyword) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = postService.findByTitle(keyword);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("findByTitle Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

}
