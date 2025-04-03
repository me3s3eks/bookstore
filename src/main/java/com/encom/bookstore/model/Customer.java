package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @EqualsAndHashCode.Exclude
    private Long id;

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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
