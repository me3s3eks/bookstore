package com.encom.bookstore.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedBookId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private BookVariantId bookVariantId;
}
