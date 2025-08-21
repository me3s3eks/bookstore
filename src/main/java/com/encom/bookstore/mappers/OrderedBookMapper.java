package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.OrderedBookResponseDto;
import com.encom.bookstore.model.OrderedBook;

public interface OrderedBookMapper {

    OrderedBookResponseDto toOrderedBookResponseDto(OrderedBook orderedBook);
}
