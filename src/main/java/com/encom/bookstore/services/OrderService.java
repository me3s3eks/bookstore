package com.encom.bookstore.services;

import com.encom.bookstore.dto.OrderDto;
import com.encom.bookstore.dto.OrderFilterDto;
import com.encom.bookstore.dto.OrderRequestDto;
import com.encom.bookstore.dto.OrderResponseDto;
import com.encom.bookstore.dto.OrderUpdateDto;
import com.encom.bookstore.dto.OrderedBookResponseDto;
import com.encom.bookstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    Page<OrderResponseDto> findOrdersByFilterDto(Pageable pageable, OrderFilterDto orderFilterDto);

    OrderDto findOrder(long orderId);

    Order getOrder(long orderId);

    List<OrderedBookResponseDto> findOrderedBooksForOrder(long orderId);

    void updateOrder(long orderId, OrderUpdateDto orderUpdateDto);

    BigDecimal getTotalPrice(Order order);
}
