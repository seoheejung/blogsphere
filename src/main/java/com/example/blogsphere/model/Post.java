package com.example.blogsphere.model;

import java.util.Date;
import com.example.blogsphere.model.enun.Visibility;
import jakarta.persistence.*;


@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("글 ID")
    private Long id;

    @Column
    @org.hibernate.annotations.Comment("블로그 ID")
    private Long blogId;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("글 제목")
    private String title;

    @Column(columnDefinition = "TEXT")
    @org.hibernate.annotations.Comment("글 내용")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column
    @org.hibernate.annotations.Comment("발행상태 (PRIVATE, PROTECTED, PUBLIC)")
    private Visibility visibility;

    @Column(name = "category_id")
    @org.hibernate.annotations.Comment("카테고리 ID")
    private Long categoryId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Comment("발행시간")
    private Date published;

    @Column
    @org.hibernate.annotations.Comment("태그")
    private String tag;

    @Column(name = "accept_comment")
    @org.hibernate.annotations.Comment("댓글 허용 여부")
    private Boolean acceptComment;

    @Column
    @org.hibernate.annotations.Comment("보호글 비밀번호")
    private String password;

    // getters, setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return this.blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getPublished() {
        return this.published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getAcceptComment() {
        return this.acceptComment;
    }

    public void setAcceptComment(Boolean acceptComment) {
        this.acceptComment = acceptComment;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
