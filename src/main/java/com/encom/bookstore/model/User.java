package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "users_sequence",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Long id = null;

    @Column(length = 255, nullable = false, unique = true)
    @NaturalId
    private String login;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(length = 50, nullable = false)
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(length = 50)
    @EqualsAndHashCode.Exclude
    private String patronymic;

    @Column(length = 50, nullable = false)
    @EqualsAndHashCode.Exclude
    private String surname;

    @Column(name = "date_of_birth")
    @EqualsAndHashCode.Exclude
    private LocalDate dateOfBirth;

    @Column(length = 255, nullable = false, unique = true)
    @NaturalId
    private String email;

    @Column(name = "deleted_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime timeOfRemoval;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Role> roles;
}
