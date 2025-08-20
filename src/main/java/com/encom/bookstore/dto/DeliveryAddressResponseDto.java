package com.encom.bookstore.dto;

public record DeliveryAddressResponseDto(

    long id,

    String country,

    String city,

    String postalCode,

    String street,

    String building,

    String office) {
}
