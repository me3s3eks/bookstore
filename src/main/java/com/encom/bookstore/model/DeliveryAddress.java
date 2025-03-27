package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_addresses")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {
    @Id
    @SequenceGenerator(
            name = "delivery_address_seq",
            sequenceName = "delivery_addresses_sequence",
            allocationSize = 5
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_address_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private Customer customer;

    @Column(length = 60, nullable = false)
    private String country;

    @Column(length = 60, nullable = false)
    private String city;

    @Column(name = "postal_code", length = 12, nullable = false)
    private String postalCode;

    @Column(length = 60, nullable = false)
    private String street;

    @Column(length = 12, nullable = false)
    private String building;

    @Column(length = 5)
    private String office;

    @Column(name = "deleted_at")
    private LocalDateTime timeOfRemoval;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryAddress)) return false;
        DeliveryAddress other = (DeliveryAddress) o;
        if (!other.canEqual(this)) return false;
        return id != null && id.equals(other.getId());
    }

    protected boolean canEqual(Object other) {
        return other instanceof DeliveryAddress;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
