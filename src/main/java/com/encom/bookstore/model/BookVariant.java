package com.encom.bookstore.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "book_variants")
@NamedEntityGraph(
    name = "BookVariant.paperBookProperties",
    attributeNodes = {
        @NamedAttributeNode("paperBookProperties")
    }
)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookVariant {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "bookType", column = @Column(name = "book_type"))
    })
    private BookVariantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "product_status", nullable = false)
    private AvailabilityStatus availabilityStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "paper_book_properties_id")
    private PaperBookProperties paperBookProperties;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookVariant)) return false;
        BookVariant other = (BookVariant) o;
        if (!other.canEqual(this)) return false;
        return id != null && id.equals(other.getId());
    }

    protected boolean canEqual(Object other) {
        return other instanceof BookVariant;
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}
