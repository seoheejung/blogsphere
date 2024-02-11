package com.example.blogsphere.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.blogsphere.model.Auth;

@Mapper
public interface AuthMapper  {
    
    // 인증 코드 등록
    void insertAuthCode(@Param("authCode") Auth authCode);

    // 클라이언트 ID 유효성 검증
    boolean existsByClientId(@Param("clientEmail") String clientEmail);

    // 인증코드 유효성 검사
    boolean isValidAuthCode(@Param("code") String code, @Param("clientEmail") String clientEmail, @Param("clientPass") String clientPass);
}

