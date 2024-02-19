package com.example.blogsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("블로그 ID")
    private Long id;

    @Column(nullable = false, unique = true)
    @org.hibernate.annotations.Comment("블로그 URL")
    @NotBlank(message = "url is not Null")
    private String url;

    @Column(name = "user_id", nullable = false)
    @org.hibernate.annotations.Comment("사용자 ID")
    @NotNull(message = "userId is not Null")
    private Long userId;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("블로그 타이틀")
    @NotBlank(message = "title is not Null")
    private String title;

    @Column
    @org.hibernate.annotations.Comment("블로그 설명")
    private String description;

    @Column(name = "is_default")
    @org.hibernate.annotations.Comment("대표블로그 여부 (Y/N)")
    private String isDefault;

    @Column
    @org.hibernate.annotations.Comment("블로그에서의 닉네임")
    private String nickname;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @org.hibernate.annotations.Comment("블로그 생성시간")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // getters, setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
