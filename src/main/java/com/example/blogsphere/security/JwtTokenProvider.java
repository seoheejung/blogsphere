package com.example.blogsphere.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private SecretKey secretKey;

    public String resolveToken(HttpServletRequest request) {
        if(request.getHeader("authorization") != null )
            return request.getHeader("authorization").substring(7);
        return null;
    }
    
    // 토큰에서 클레임 추출
    public String getClaimFromToken(String token, String claimKey) {
        Jws<Claims> claimsJws = getJwtParser().parseClaimsJws(token);
        return claimsJws.getBody().get(claimKey, String.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            getJwtParser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 적절한 예외 처리
            return false;
        }
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public boolean checkToken (String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            if (validateToken(token)) { // 토큰 유효성 검사
                String role = getClaimFromToken(token, "role");
                if ("ADMIN".equals(role)) { // 관리자인 경우
                        return true;
                } 
            }
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
