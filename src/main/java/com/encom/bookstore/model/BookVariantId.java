package com.encom.bookstore.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookVariantId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long bookId;
    private BookType bookType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookVariantId)) return false;
        BookVariantId other = (BookVariantId) o;
        if (!other.canEqual(this)) return false;
        if (!(bookId != null && bookId.equals(other.getBookId()))) return false;
        return bookType != null && (bookType == other.getBookType());
    }

    protected boolean canEqual(Object other) {
        return other instanceof BookVariantId;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
