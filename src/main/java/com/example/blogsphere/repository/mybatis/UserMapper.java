package com.example.blogsphere.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.blogsphere.model.User;

@Mapper
public interface UserMapper {

    // 로그인
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}

