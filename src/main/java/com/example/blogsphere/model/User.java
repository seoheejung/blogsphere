package com.example.blogsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("사용자 ID")
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

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @org.hibernate.annotations.Comment("계정 생성 시간")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @org.hibernate.annotations.Comment("계정 정보 수정 시간")
    private LocalDateTime updatedAt;

    /* 데이터베이스 레벨이 아닌 JPA 엔티티 레벨에서 createdAt 및 updatedAt 필드를 
    자동으로 관리하려면, JPA 생명주기 이벤트 어노테이션인 
    @PrePersist 및 @PreUpdate를 사용 */
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
