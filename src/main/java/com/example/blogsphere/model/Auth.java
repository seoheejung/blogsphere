package com.example.blogsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "auths")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("code ID")
    private Long id;

    @Column(unique = true, nullable = false)
    @org.hibernate.annotations.Comment("인증코드")
    @NotBlank(message = "code is not Null")
    private String code;

    @Column(name = "client_email", nullable = false)
    @org.hibernate.annotations.Comment("사용자 이메일")
    @NotBlank(message = "clientEmail is not Null")
    private String clientEmail;

    @Column(name = "expiration_time")
    @org.hibernate.annotations.Comment("만료시간")
    private LocalDateTime expirationTime;

    // Getters, Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

}
