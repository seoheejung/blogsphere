package com.example.blogsphere.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    public CustomBasicAuthenticationFilter() {
        super(null); // AuthenticationManager는 필터에서 사용하지 않으므로 null 전달
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Basic ")) {
            String[] credentials = extractCredentials(header);
            UserDetails userDetails = userDetailsService().loadUserByUsername(credentials[0]);
            if (userDetails != null && userDetails.getPassword().equals(credentials[1])) {
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }

    private String[] extractCredentials(String header) {
        byte[] decoded = Base64.getDecoder().decode(header.substring(6));
        String decodedString = new String(decoded);
        String[] parts = decodedString.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid basic authentication token");
        }
        return parts; // 사용자명과 비밀번호 반환
    }

    // CustomBasicAuthenticationFilter와 UserDetailsService 간의 직접적인 의존성을 제거
    private UserDetailsService userDetailsService() {
        // ApplicationContext에서 UserDetailsService 빈을 가져오는 로직 구현
        return applicationContext.getBean(UserDetailsService.class);
    }
}