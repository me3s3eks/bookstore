package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "paper_books_properties")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperBookProperties {
    @Id
    @SequenceGenerator(
            name = "paper_book_prop_seq",
            sequenceName = "paper_books_properties_sequence",
            allocationSize = 5
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paper_book_prop_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "quantity_in_stock", nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    private int width; //value in millimeters

    @Column(nullable = false)
    private int height; //value in millimeters

    @Column(nullable = false)
    private int depth; //value in millimeters

    @Column(nullable = false)
    private BigDecimal weight; //value in kilograms

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperBookProperties)) return false;
        PaperBookProperties other = (PaperBookProperties) o;
        if (!other.canEqual(this)) return false;
        return id != null && id.equals(other.getId());
    }

    protected boolean canEqual(Object other) {
        return other instanceof PaperBookProperties;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
