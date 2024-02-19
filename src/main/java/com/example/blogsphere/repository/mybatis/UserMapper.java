package com.example.blogsphere.repository.mybatis;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper  {
    // 로그인
    Map<String, Object> login(@Param("username") String username);
}

