package com.encom.bookstore.services;

import com.encom.bookstore.dto.DeliveryAddressRequestDto;
import com.encom.bookstore.dto.DeliveryAddressResponseDto;
import com.encom.bookstore.model.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {

    DeliveryAddressResponseDto addAddress(DeliveryAddressRequestDto addressRequestDto);

    List<DeliveryAddressResponseDto> findAllAddresses();

    void updateAddress(long addressId, DeliveryAddressRequestDto addressRequestDto);

    void deleteAddress(long addressId);

    DeliveryAddress getAddress(long addressId);
}
