package com.example.blogsphere.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogsphere.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일을 기준으로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 사용자명을 기준으로 사용자 찾기
    Optional<User> findByUsername(String username);

    // 특정 이메일이 사용되었는지 여부 확인
    boolean existsByEmail(String email);

    // 활성화된 사용자 목록 검색
    List<User> findByIsActive(Boolean isActive);

    // 특정 이름을 포함하는 사용자 찾기
    List<User> findByUsernameContaining(String name);

    // 특정 역할을 가진 사용자 목록 검색
    List<User> findByRole(String role);

}