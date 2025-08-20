package com.encom.bookstore.dto;

public record DeliveryAddressRequestDto(

    String country,

    String city,

    String postalCode,

    String street,

    String building,

    String office) {
}
