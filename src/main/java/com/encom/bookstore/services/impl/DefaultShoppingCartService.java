package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.CartItemRequestDto;
import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.dto.ShoppingCartResponseDto;
import com.encom.bookstore.dto.ShoppingCartTotalPriceDto;
import com.encom.bookstore.dto.ShoppingCartTotalTypesDto;
import com.encom.bookstore.exceptions.BookVariantNotAvailableException;
import com.encom.bookstore.exceptions.CartAlreadyContainsItemException;
import com.encom.bookstore.exceptions.CartNotContainsItemException;
import com.encom.bookstore.mappers.CartItemMapper;
import com.encom.bookstore.mappers.ShoppingCartMapper;
import com.encom.bookstore.model.AvailabilityStatus;
import com.encom.bookstore.model.BookType;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import com.encom.bookstore.model.CartItem;
import com.encom.bookstore.services.BookVariantService;
import com.encom.bookstore.services.ShoppingCartService;
import com.encom.bookstore.sessions.ShoppingCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultShoppingCartService implements ShoppingCartService {

    private final ShoppingCart shoppingCart;

    private final ShoppingCartMapper shoppingCartMapper;

    private final CartItemMapper cartItemMapper;

    private final BookVariantService bookVariantService;

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartTotalTypesDto getTotalDistinctItems() {
        int totalDistinctItems = shoppingCart.getCartItems().size();
        return new ShoppingCartTotalTypesDto(totalDistinctItems);
    }

    @Override
    public ShoppingCartTotalPriceDto getTotalPrice() {
        BigDecimal totalPrice = shoppingCart.getCartItems().stream()
            .map(CartItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ShoppingCartTotalPriceDto(totalPrice);
    }

    @Override
    public CartItemResponseDto addItemToCart(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequestDto);

        if (shoppingCart.containsItem(cartItem)) {
            throw new CartAlreadyContainsItemException(cartItem.getBookId(), cartItem.getBookType());
        }

        checkBookVariantAvailability(cartItem);

        shoppingCart.addItem(cartItem);

        return cartItemMapper.toCartItemResponseDto(cartItem);
    }

    @Override
    public CartItemResponseDto updateCartItem(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequestDto);

        if (!shoppingCart.containsItem(cartItem)) {
            throw new CartNotContainsItemException(cartItem.getBookId(), cartItem.getBookType());
        }

        checkBookVariantAvailability(cartItem);

        shoppingCart.updateItem(cartItem);

        return cartItemMapper.toCartItemResponseDto(cartItem);
    }

    @Override
    public void clearCart() {
        shoppingCart.clear();
    }

    @Override
    public void removeCartItem(CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequestDto);
        shoppingCart.removeItem(cartItem);
    }

    private void checkBookVariantAvailability(CartItem cartItem) {
        BookVariantId bookVariantId = new BookVariantId(cartItem.getBookId(), cartItem.getBookType());
        BookVariant bookVariant = bookVariantService.getBookVariant(bookVariantId);

        AvailabilityStatus availabilityStatus = bookVariant.getAvailabilityStatus();
        if (availabilityStatus == AvailabilityStatus.UNAVAILABLE) {
            throw new BookVariantNotAvailableException(bookVariant.getId().getBookId(),
                bookVariant.getId().getBookType(), bookVariant.getAvailabilityStatus());
        }

        if (availabilityStatus == AvailabilityStatus.ON_ORDER) {
            return;
        }

        BookType bookType = bookVariant.getId().getBookType();
        if (bookType == BookType.HARDCOVER_BOOK || bookType == BookType.PAPER_BOOK) {
            if (availabilityStatus == AvailabilityStatus.IN_STOCK) {
                int requestedQuantity = cartItem.getQuantity();
                int currentQuantity = bookVariant.getPaperBookProperties().getQuantityInStock();
                if (requestedQuantity > currentQuantity) {
                    throw new BookVariantNotAvailableException(bookVariant.getId().getBookId(),
                        bookVariant.getId().getBookType(), bookVariant.getAvailabilityStatus(), currentQuantity);
                }
            }
        }
    }
}
