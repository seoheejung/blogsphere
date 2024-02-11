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
import java.util.List;

import com.example.blogsphere.CommonFunction;
import com.example.blogsphere.model.ResultMessage;
import com.example.blogsphere.model.User;
import com.example.blogsphere.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 생성 및 수정
    @PostMapping
    public ResponseEntity<ResultMessage> createUser(@RequestBody User user) {
        logger.info(CommonFunction.getClassName());
        try {
            User savedUser = userService.saveUser(user);
            ResultMessage resultMessage = ResultMessage.builder()
                                                        .code(200)
                                                        .msg("success")
                                                        .result(savedUser)
                                                        .build();
            return ResponseEntity.ok(resultMessage);
        } catch (Exception e) {
            logger.error("createUser Error: {}", e.getMessage());
            ResultMessage resultMessage = ResultMessage.builder()
                                                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                        .msg("Internal server error: " + e.getMessage())
                                                        .result("F")
                                                        .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
        }
    }

    // ID로 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage> getUserById(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            return userService.getUserById(id)
                    .map(user -> ResponseEntity.ok(ResultMessage.builder()
                                                            .code(200)
                                                            .msg("success")
                                                            .result(user)
                                                            .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                        .body(ResultMessage.builder()
                                                            .code(404)
                                                            .msg("User not found")
                                                            .result("F")
                                                            .build()));
        } catch (Exception e) {
            logger.error("getUserById Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .msg("Internal server error: " + e.getMessage())
                                    .result("F")
                                    .build());
        }
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<ResultMessage> getAllUsers() {
        logger.info(CommonFunction.getClassName());
        try {
            List<User> users = userService.getAllUsers();
            ResultMessage resultMessage = ResultMessage.builder()
                                                .code(200)
                                                .msg("success")
                                                .result(users)
                                                .build();
            return ResponseEntity.ok(resultMessage);
        } catch (Exception e) {
            logger.error("getAllUsers Error: {}", e.getMessage());
            ResultMessage resultMessage = ResultMessage.builder()
                                                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                        .msg("Internal server error: " + e.getMessage())
                                                        .result("F")
                                                        .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);
        }
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage> deleteUser(@PathVariable Long id) {
        logger.info(CommonFunction.getClassName());
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("User deleted successfully")
                                                    .result("Success")
                                                    .build());
        } catch (Exception e) {
            logger.error("deleteUser Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(ResultMessage.builder()
                                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                            .msg("Internal server error: " + e.getMessage())
                                            .result("F")
                                            .build());
        }
    }

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<ResultMessage> login(@RequestParam String username, @RequestParam String password) {
        logger.info(CommonFunction.getClassName());
        try {
            User user = userService.login(username, password);
            if (user != null) {
                return ResponseEntity.ok(ResultMessage.builder()
                                                    .code(200)
                                                    .msg("Login successful")
                                                    .result(user)
                                                    .build());
            } else {
                return ResponseEntity.badRequest()
                                    .body(ResultMessage.builder()
                                                        .code(HttpStatus.BAD_REQUEST.value())
                                                        .msg("Login failed")
                                                        .result("F")
                                                        .build());
            }
        } catch (Exception e) {
            logger.error("login Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 이메일로 사용자 조회
    @GetMapping("/email/{email}")
    public ResponseEntity<ResultMessage> getUserByEmail(@PathVariable String email) {
        logger.info(CommonFunction.getClassName());
        try {
            return userService.findByEmail(email)
                    .map(user -> ResponseEntity.ok(ResultMessage.builder()
                                                                .code(200)
                                                                .msg("success")
                                                                .result(user)
                                                                .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ResultMessage.builder()
                                                                .code(404)
                                                                .msg("User not found")
                                                                .result("F")
                                                                .build()));
        } catch (Exception e) {
            logger.error("getUserByEmail Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 사용자명으로 사용자 조회
    @GetMapping("/username/{username}")
    public ResponseEntity<ResultMessage> getUserByUsername(@PathVariable String username) {
        logger.info(CommonFunction.getClassName());
        try {
            return userService.findByUsername(username)
                    .map(user -> ResponseEntity.ok(ResultMessage.builder()
                                                                .code(200)
                                                                .msg("success")
                                                                .result(user)
                                                                .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ResultMessage.builder()
                                                                .code(404)
                                                                .msg("User not found")
                                                                .result("F")
                                                                .build()));
        } catch (Exception e) {
            logger.error("getUserByUsername Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }

    // 특정 이름을 포함하는 사용자 검색
    @GetMapping("/search/{name}")
    public ResponseEntity<ResultMessage> searchUsersByName(@PathVariable String name) {
        logger.info(CommonFunction.getClassName());
        try {
            List<User> users = userService.findByUsernameContaining(name);
            return ResponseEntity.ok(ResultMessage.builder()
                                                    .code(200)
                                                    .msg("success")
                                                    .result(users)
                                                    .build());
        } catch (Exception e) {
            logger.error("searchUsersByName Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ResultMessage.builder()
                                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                    .msg("Internal server error: " + e.getMessage())
                                                    .result("F")
                                                    .build());
        }
    }
}
