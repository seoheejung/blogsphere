package com.example.blogsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .cors(cors -> cors.configurationSource(corsConfigurationSource)) // CORS 설정 적용
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/users/login", "/authorize", "access-token").permitAll() // 경로에 대한 접근 허용
                .anyRequest().authenticated() // 그 외 모든 요청에 대해 인증 필요
            )
            .httpBasic(Customizer.withDefaults());  // 기본 HTTP 인증 사용

        return http.build();
    }
}
