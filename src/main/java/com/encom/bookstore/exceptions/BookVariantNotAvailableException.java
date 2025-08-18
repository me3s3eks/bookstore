package com.encom.bookstore.exceptions;

import com.encom.bookstore.model.AvailabilityStatus;
import com.encom.bookstore.model.BookType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookVariantNotAvailableException extends RuntimeException {

    private final long bookId;

    private final BookType bookType;

    private final AvailabilityStatus availabilityStatus;

    private Integer availableQuantity = null;
}
