package com.example.blogsphere.model;

import jakarta.persistence.*;

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

    @Column
    @org.hibernate.annotations.Comment("부모 카테고리 ID")
    private Long parentId;

    @Column
    @org.hibernate.annotations.Comment("사용자 ID")
    private Long userId;

    @Column
    @org.hibernate.annotations.Comment("블로그 ID")
    private Long blogId;

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


}


