package com.example.blogsphere.service;

import com.example.blogsphere.model.Auth;
import com.example.blogsphere.repository.mybatis.AuthMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;

    // 클라이언트 ID 유효성 검증 및 인증 코드 저장
    public boolean createAndStoreAuthCode(String clientEmail, String authCode) {
        // 클라이언트 ID 유효성 검증
        if (!authMapper.existsByClientId(clientEmail)) {
            return false;
        }

        Auth newAuth = new Auth();
        newAuth.setCode(authCode);
        newAuth.setClientEmail(clientEmail);
        newAuth.setExpirationTime(LocalDateTime.now().plusHours(1)); // 1시간 후 만료

        authMapper.insertAuthCode(newAuth);

        return true;
    }

    public String generateAccessToken(String clientEmail, String clientPass, String code) {
        // 클라이언트 인증 및 인증 코드 검증 로직
        if (authMapper.isValidAuthCode(code, clientEmail, clientPass)) {
            long currentTimeMillis = System.currentTimeMillis();
            SecretKey signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

            return Jwts.builder()
                    .setSubject(clientEmail)
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + 3600000)) // 1시간 유효
                    .signWith(signingKey, SignatureAlgorithm.HS512) // 비밀키로 서명
                    .compact();
        }
        return null;
    }
}
