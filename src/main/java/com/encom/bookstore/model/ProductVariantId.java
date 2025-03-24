package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Setter
public class ProductVariantId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long productId;
    private ProductType productType;
}
