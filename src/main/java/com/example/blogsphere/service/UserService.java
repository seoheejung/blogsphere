package com.example.blogsphere.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.blogsphere.model.User;
import com.example.blogsphere.repository.jpa.UserRepository;
import com.example.blogsphere.repository.mybatis.UserMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    
    // JpaRepository의 기본 메소드들
    @SuppressWarnings("null")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> saveUser(@NotNull User user) {
        // 사용자 생성 및 수정 로직
        Map<String, Object> userInfo = new HashMap<>();
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        // 비밀번호 정보 제거가 안되서 필요한 사용자 정보를 맵에 담아 반환
        userInfo.put("id", savedUser.getId());
        userInfo.put("username", savedUser.getUsername());
        userInfo.put("email", savedUser.getEmail());
        return userInfo;
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
    public List<User> findByUsernameContaining(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    // MyBatis를 사용한 로그인 기능
    @Transactional(readOnly = true)
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> userInfo = userMapper.login(username);
        
        if (userInfo != null && !userInfo.isEmpty()) {
            String storedPassword = (String) userInfo.get("password");
    
            // 입력된 비밀번호와 저장된 암호화된 비밀번호 비교
            if (passwordEncoder.matches(password, storedPassword)) {
                long currentTimeMillis = System.currentTimeMillis();
                SecretKey signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
                String token = Jwts.builder()
                            .setSubject(username)
                            .claim("userId", userInfo.get("userId"))
                            .claim("useremail", userInfo.get("useremail"))
                            .claim("role", userInfo.get("role"))
                            .setIssuedAt(new Date(currentTimeMillis))
                            .setExpiration(new Date(currentTimeMillis + 3600000)) // 1시간 유효
                            .signWith(signingKey, SignatureAlgorithm.HS512) // 비밀키로 서명
                            .compact();
    
                // JWT 토큰과 사용자 정보를 Map에 추가
                userInfo.put("token", token);
                userInfo.remove("password"); // 비밀번호 정보는 제거
            }
        }
        return userInfo;
    }
}


