package com.encom.bookstore.services.impl;

import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.OrderedBook;
import com.encom.bookstore.model.OrderedBookId;
import com.encom.bookstore.repositories.OrderedBookRepository;
import com.encom.bookstore.services.BookVariantService;
import com.encom.bookstore.services.OrderedBookService;
import com.encom.bookstore.sessions.ShoppingCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultOrderedBookService implements OrderedBookService {

    private final OrderedBookRepository orderedBookRepository;

    private final BookVariantService bookVariantService;

    @Override
    @Transactional
    public void addBooksToOrder(Order order, ShoppingCart shoppingCart) {
        shoppingCart.getCartItems().forEach(cartItem -> {
            BookVariantId bookVariantId = new BookVariantId(cartItem.getBookId(), cartItem.getBookType());
            BookVariant bookVariant = bookVariantService.getBookVariantForUpdate(bookVariantId);
            bookVariantService.checkBookVariantAvailability(bookVariantId, cartItem);
            OrderedBookId orderedBookId = new OrderedBookId(order.getId(), bookVariantId);

            OrderedBook orderedBook = OrderedBook.builder()
                .id(orderedBookId)
                .order(order)
                .bookVariant(bookVariant)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getItemPrice())
                .build();
            orderedBookRepository.save(orderedBook);
            bookVariantService.updateBookVariantAfterAddToOrder(bookVariantId, cartItem);
        });
    }

    @Override
    public List<OrderedBook> getOrderedBooksForOrder(Order order) {
        return orderedBookRepository.getOrderedBookByOrder(order);
    }
}
