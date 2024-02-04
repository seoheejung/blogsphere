package com.example.blogsphere.service;

import org.springframework.stereotype.Service;
import com.example.blogsphere.model.User;
import com.example.blogsphere.repository.jpa.UserRepository;
import com.example.blogsphere.repository.mybatis.UserMapper;

import jakarta.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public User saveUser(@NotNull User user) {
        // 사용자 생성 및 수정 로직
        return userRepository.save(user);
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Optional<User> getUserById(@NotNull Long id) {
        // 사용자 조회 로직
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // 모든 사용자 조회 로직
        return userRepository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUser(@NotNull Long id) {
        // 사용자 삭제 로직
        userRepository.deleteById(id);
    }

    // JPA를 사용한 사용자 조회
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findByIsActive(Boolean isActive) {
        return userRepository.findByIsActive(isActive);
    }

    @Transactional(readOnly = true)
    public List<User> findByUsernameContaining(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    // MyBatis를 사용한 로그인 기능
    @Transactional(readOnly = true)
    public User login(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }

}