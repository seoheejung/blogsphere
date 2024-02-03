package com.example.blogsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("사용자 ID")
    @NotBlank(message = "id is not Null")
    private Long id;

    @Column(nullable = false, length = 50)
    @org.hibernate.annotations.Comment("사용자명")
    @NotBlank(message = "username is not Null")
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    @org.hibernate.annotations.Comment("이메일 주소")
    @NotBlank(message = "email is not Null")
    private String email;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("비밀번호")
    @NotBlank(message = "password is not Null")
    private String password;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Comment("계정 생성 시간")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Comment("계정 정보 수정 시간")
    private Date updatedAt;

    // getters, setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
