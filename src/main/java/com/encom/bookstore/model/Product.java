package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "products_sequence",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Set<ProductVariant> productVariants;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Column(nullable = false)
    private short edition;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private Language language;

    @Column(name = "page_count", nullable = false)
    private short pageCount;

    private String series;

    @Column(nullable = false)
    private String isbn;

    @Column(name = "deleted_at")
    private LocalDateTime timeOfRemoval;
}
