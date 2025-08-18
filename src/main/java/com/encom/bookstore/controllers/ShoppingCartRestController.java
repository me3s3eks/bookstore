package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.CartItemRequestDto;
import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.dto.ShoppingCartResponseDto;
import com.encom.bookstore.dto.ShoppingCartTotalPriceDto;
import com.encom.bookstore.dto.ShoppingCartTotalTypesDto;
import com.encom.bookstore.services.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDto> addItemToCart(
        @Valid @RequestBody CartItemRequestDto cartItemRequestDto,
        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        CartItemResponseDto cartItemResponseDto = shoppingCartService.addItemToCart(cartItemRequestDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cartItemResponseDto);
    }

    @GetMapping()
    public ResponseEntity<ShoppingCartResponseDto> getShoppingCart() {
        ShoppingCartResponseDto shoppingCartResponseDto = shoppingCartService.getShoppingCart();
        return ResponseEntity.ok(shoppingCartResponseDto);
    }

    @GetMapping("/total-types")
    public ResponseEntity<ShoppingCartTotalTypesDto> getTotalTypes() {
        ShoppingCartTotalTypesDto totalTypesDto = shoppingCartService.getTotalDistinctItems();
        return ResponseEntity.ok(totalTypesDto);
    }

    @GetMapping("/total-price")
    public ResponseEntity<ShoppingCartTotalPriceDto> getTotalPrice() {
        ShoppingCartTotalPriceDto totalPriceDto = shoppingCartService.getTotalPrice();
        return ResponseEntity.ok(totalPriceDto);
    }

    //~exclude negative id pattern (delete dash)
    @PutMapping("/items")
    public ResponseEntity<CartItemResponseDto> updateCartItem(
        @Valid @RequestBody CartItemRequestDto cartItemRequestDto,
        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        CartItemResponseDto cartItemResponseDto = shoppingCartService.updateCartItem(cartItemRequestDto);
        return ResponseEntity.ok(cartItemResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        shoppingCartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> deleteCartItem(@Valid @RequestBody CartItemRequestDto cartItemRequestDto,
                                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        shoppingCartService.removeCartItem(cartItemRequestDto);
        return ResponseEntity.noContent().build();
    }
}
