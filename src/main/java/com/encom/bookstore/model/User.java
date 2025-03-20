package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(length = 255, nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "deleted_at")
    private LocalDateTime timeOfRemoval;
}
