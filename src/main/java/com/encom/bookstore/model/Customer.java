package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50)
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
