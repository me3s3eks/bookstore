package com.encom.bookstore.specifications;

import com.encom.bookstore.model.DeliveryAddress;
import com.encom.bookstore.model.DeliveryAddress_;
import com.encom.bookstore.model.User;
import org.springframework.data.jpa.domain.Specification;

public class DeliveryAddressSpecifications {

    public static Specification<DeliveryAddress> userEquals(User user) {
        if (user == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(DeliveryAddress_.user), user);
    }

    public static Specification<DeliveryAddress> countryEquals(String country) {
        if (country == null || country.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.country)), country.toLowerCase());
    }

    public static Specification<DeliveryAddress> cityEquals(String city) {
        if (city == null || city.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.city)), city.toLowerCase());
    }

    public static Specification<DeliveryAddress> postalCodeEquals(String postalCode) {
        if (postalCode == null || postalCode.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.postalCode)), postalCode.toLowerCase());
    }

    public static Specification<DeliveryAddress> streetEquals(String street) {
        if (street == null || street.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.street)), street.toLowerCase());
    }

    public static Specification<DeliveryAddress> buildingEquals(String building) {
        if (building == null || building.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.building)), building.toLowerCase());
    }

    public static Specification<DeliveryAddress> officeEquals(String office) {
        if (office == null || office.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(cb.lower(root.get(DeliveryAddress_.office)), office.toLowerCase());
    }
}


