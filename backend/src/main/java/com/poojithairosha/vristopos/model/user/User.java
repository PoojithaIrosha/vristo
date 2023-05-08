package com.poojithairosha.vristopos.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isEnabled;

    private String verificationCode;

    public User(String name, String username, String password, String mobile, String email, LocalDateTime createdAt, UserRole role, Boolean isEnabled) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
        this.createdAt = createdAt;
        this.role = role;
        this.isEnabled = isEnabled;
    }
}
