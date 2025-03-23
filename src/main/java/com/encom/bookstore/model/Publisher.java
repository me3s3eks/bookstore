package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "publishers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private short id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "publisher")
    private Set<Product> products;
}
