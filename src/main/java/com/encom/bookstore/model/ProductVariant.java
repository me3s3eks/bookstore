package com.encom.bookstore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
@IdClass(ProductVariantId.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductVariant {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "productId", column = @Column(name = "product_id")),
            @AttributeOverride(name = "productType", column = @Column(name = "product_type"))
    })
    private ProductVariantId id;

    @MapsId("productId")
    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "quantity_in_stock")
    private int quantityInStock;

    private short width; //value in millimeters

    private short height; //value in millimeters

    private short depth; //value in millimeters

    private BigDecimal weight; //value in kilograms

    @Column(name = "product_status", nullable = false)
    private ProductStatus productStatus;
}
