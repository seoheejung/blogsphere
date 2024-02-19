package com.example.blogsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("카테고리 ID")
    private Long id;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("카테고리 이름")
    private String name;

    @Column(name = "parent_id")
    @org.hibernate.annotations.Comment("부모 카테고리 ID")
    private Long parentId;

    @Column(name = "user_id", nullable = false)
    @org.hibernate.annotations.Comment("사용자 ID")
    private Long userId;

    @Column(name = "blog_id", nullable = false)
    @org.hibernate.annotations.Comment("블로그 ID")
    private Long blogId;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @org.hibernate.annotations.Comment("생성시간")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @org.hibernate.annotations.Comment("수정시간")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters, setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return this.blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}


