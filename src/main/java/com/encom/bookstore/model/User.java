package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "users_sequence",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(name = "deleted_at")
    private LocalDateTime timeOfRemoval;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;
}
