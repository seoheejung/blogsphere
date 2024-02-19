package com.example.blogsphere.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.util.List;
import java.util.Collections;
import javax.crypto.SecretKey;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter() {
        // AuthenticationManager는 Spring Security에서 인증 과정을 관리하는 주요 컴포넌트로, 필터에서 인증 로직을 수행하는 데 사용
        // super(authenticationManager);
        super(null); // AuthenticationManager는 필터에서 사용하지 않으므로 null 전달
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Authentication authentication = getAuthenticationFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException ex) {
                SecurityContextHolder.clearContext(); // 인증 실패 시 컨텍스트를 클리어
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private Authentication getAuthenticationFromToken(String token) throws JwtException {
        SecretKey secretKey = jwtSigningKey(); // SecretKey를 메소드 호출로 가져오기
        // JWT 토큰의 서명을 검증하고, 클레임을 추출
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        String username = claimsJws.getBody().getSubject();

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // 순환참조 때문에 SecretKey를 직접 주입받지 않고, 대신 필터 내부의 메소드에서 SecretKey를 동적으로 가져오는 방식으로 구현
    private SecretKey jwtSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}