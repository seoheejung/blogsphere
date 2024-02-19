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
import com.example.blogsphere.model.Blog;
import com.example.blogsphere.model.Post;
import com.example.blogsphere.model.ResultMessage;
import com.example.blogsphere.service.BlogService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    private final CommonFunction commonFunction;

    // 블로그 생성 및 수정
    @PostMapping
    public ResponseEntity<ResultMessage> saveBlog(@RequestBody Blog blog) {
        logger.info(CommonFunction.getClassName());
        try {
            Blog savedBlog = blogService.saveBlog(blog);
            ResultMessage resultMessage = ResultMessage.builder()
                                                    .code(200)
                                                    .msg("success")
                                                    .result(savedBlog)
                                                    .build();
            return ResponseEntity.ok(resultMessage);
        } catch (Exception e) {
            logger.error("saveBlog Error: {}", e.getMessage());
            ResultMessage resultMessage = ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
        }
    }

    // 특정 블로그 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage> getBlogById(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            return blogService.getBlogById(id)
                    .map(blog -> ResponseEntity.ok(ResultMessage.builder()
                                                            .code(200)
                                                            .msg("success")
                                                            .result(blog)
                                                            .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                            .body(ResultMessage.builder()
                                                            .code(404)
                                                            .msg("Blog not found")
                                                            .result("F")
                                                            .build()));
        } catch (Exception e) {
            logger.error("getBlogById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 모든 블로그 조회
    @GetMapping
    public ResponseEntity<ResultMessage> getAllBlogs() {
        logger.info(CommonFunction.getClassName());
        try {
            List<Blog> blogs = blogService.getAllBlogs();
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(blogs)
                                                .build());
        } catch (Exception e) {
            logger.error("getAllBlogs Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 블로그 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage> deleteBlog(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("Blog deleted successfully")
                                                .result("Success")
                                                .build());
        } catch (Exception e) {
            logger.error("deleteBlog Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 사용자 ID로 블로그 검색
    @PostMapping("/userId")
    public ResponseEntity<ResultMessage> findByUserId(@RequestParam Long userId) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Blog> blogs = blogService.findByUserIdContaining(userId);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(blogs)
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

    // 닉네임으로 블로그 검색
    @PostMapping("/nickname")
    public ResponseEntity<ResultMessage> findByNickname(@RequestBody String nickname) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Blog> blogs = blogService.findByNickname(nickname);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(blogs)
                                                .build());
        } catch (Exception e) {
            logger.error("findByNickname Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // URL로 블로그 검색
    @PostMapping("/url")
    public ResponseEntity<ResultMessage> findByUrl(@RequestBody String url) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Blog> blogs = blogService.findByUrl(url);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(blogs)
                                                .build());
        } catch (Exception e) {
            logger.error("findByUrl Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // post 완료 후 진행
    // 블로그 통계 조회
    @PostMapping("/statistics")
    public ResponseEntity<ResultMessage> getBlogStatistics(@RequestParam Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            Map<String, Object> statistics = blogService.getBlogStatistics(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(statistics)
                                                .build());
        } catch (Exception e) {
            logger.error("getBlogStatistics Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 단어를 포함한 블로그 게시물 검색
    @PostMapping("/search")
    public ResponseEntity<ResultMessage> searchPostsByKeyword(@RequestParam Long id, @RequestParam String keyword) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = blogService.searchPostsByKeyword(id, keyword);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("searchPostsByKeyword Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }


    // categoryId 작업 후 진행
    // 카테고리 또는 태그로 게시물 검색
    @PostMapping("/categoryOrTag")
    public ResponseEntity<ResultMessage> findPostsByCategoryOrTag(@RequestParam Long id, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) String tag) {
        logger.info(CommonFunction.getClassName());
        try {
            List<Post> posts = blogService.findPostsByCategoryOrTag(id, categoryId, tag);
            return ResponseEntity.ok(ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(posts)
                                                .build());
        } catch (Exception e) {
            logger.error("findPostsByCategoryOrTag Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

}