package com.example.blogsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("댓글 ID")
    private Long id;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("글 ID")
    private Long postId;

    @Column(nullable = false, length = 100)
    @org.hibernate.annotations.Comment("작성자")
    private String author;

    @Column
    @org.hibernate.annotations.Comment("부모 댓글 ID")
    private Long parentId;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("댓글 내용")
    private String content;

    @Column
    @org.hibernate.annotations.Comment("비밀 댓글 여부")
    private Boolean isSecret;

    @Column
    @org.hibernate.annotations.Comment("비밀 댓글 비밀번호")
    private String password;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Comment("댓글 작성시간")
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

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsSecret() {
        return this.isSecret;
    }

    public void setIsSecret(Boolean isSecret) {
        this.isSecret = isSecret;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


