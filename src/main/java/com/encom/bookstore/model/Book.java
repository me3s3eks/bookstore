package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "books_sequence",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false, length = 1000)
    @EqualsAndHashCode.Exclude
    private String title;

    @Column(length = 10000)
    @EqualsAndHashCode.Exclude
    private String description;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Author> authors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BookCategory bookCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Publisher publisher;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private int edition;

    @Column(name = "publication_date", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate publicationDate;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private Language language;

    @Column(name = "page_count", nullable = false)
    @EqualsAndHashCode.Exclude
    private int pageCount;

    @Column(length = 500)
    @EqualsAndHashCode.Exclude
    private String series;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(name = "deleted_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime timeOfRemoval;
}
