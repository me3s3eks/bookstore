package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.DeliveryAddressRequestDto;
import com.encom.bookstore.dto.DeliveryAddressResponseDto;
import com.encom.bookstore.services.DeliveryAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/users/delivery-addresses")
public class DeliveryAddressesRestController {

    private final DeliveryAddressService deliveryAddressService;

    @PostMapping
    public ResponseEntity<DeliveryAddressResponseDto> addDeliveryAddress(
        @Valid @RequestBody DeliveryAddressRequestDto addressRequestDto,
        BindingResult bindingResult,
        UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }

        DeliveryAddressResponseDto addressResponseDto = deliveryAddressService.addAddress(addressRequestDto);

        return ResponseEntity
            .created(uriBuilder
                .replacePath("/accounts/users/delivery-addresses/{addressId}")
                .build(addressResponseDto.id()))
            .body(addressResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryAddressResponseDto>> getAllDeliveryAddresses() {
        List<DeliveryAddressResponseDto> deliveryAddresses = deliveryAddressService.findAllAddresses();
        return ResponseEntity.ok(deliveryAddresses);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Void> updateDeliveryAddress(@PathVariable("addressId") long addressId,
                                                      @Valid @RequestBody DeliveryAddressRequestDto addressRequestDto,
                                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        deliveryAddressService.updateAddress(addressId, addressRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteDeliveryAddress(@PathVariable("addressId") long addressId) {
        deliveryAddressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
