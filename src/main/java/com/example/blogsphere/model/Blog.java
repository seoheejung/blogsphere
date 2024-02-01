package com.example.blogsphere.model;

import jakarta.persistence.*;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("블로그 ID")
    private Long blogId;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("티스토리 기본 URL")
    private String url;

    @Column
    @org.hibernate.annotations.Comment("사용자 ID")
    private Long userId;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("블로그 타이틀")
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

    @Column
    @org.hibernate.annotations.Comment("블로그 권한")
    private String role;

    @Column
    @org.hibernate.annotations.Comment("블로그 컨텐츠 개수")
    private Integer statistics;

    // getters, setters
    public Long getBlogId() {
        return this.blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Integer statistics) {
        this.statistics = statistics;
    }

}
