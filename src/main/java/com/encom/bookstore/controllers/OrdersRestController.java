package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.OrderDto;
import com.encom.bookstore.dto.OrderFilterDto;
import com.encom.bookstore.dto.OrderRequestDto;
import com.encom.bookstore.dto.OrderResponseDto;
import com.encom.bookstore.dto.OrderUpdateDto;
import com.encom.bookstore.dto.OrderedBookResponseDto;
import com.encom.bookstore.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/orders")
public class OrdersRestController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto,
                                                        BindingResult bindingResult,
                                                        UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);

        return ResponseEntity
            .created(uriBuilder
                .replacePath("accounts/users/orders/{orderId}")
                .build(orderResponseDto.id()))
            .body(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getOrders(Pageable pageable,
                                                    @Valid @RequestBody(required = false) OrderFilterDto orderFilterDto,
                                                    BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        Page<OrderResponseDto> ordersPage = orderService.findOrdersByFilterDto(pageable, orderFilterDto);
        return ResponseEntity.ok(ordersPage);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") long orderId) {
        OrderDto orderDto = orderService.findOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderedBookResponseDto>> getOrderedBooksForOrder(@PathVariable("orderId") long orderId) {
        List<OrderedBookResponseDto> orderedBooks = orderService.findOrderedBooksForOrder(orderId);
        return ResponseEntity.ok(orderedBooks);
    }

    @PutMapping(("/{orderId}"))
    public ResponseEntity<Void> updateOrder(@PathVariable("orderId") long orderId,
                                            @Valid @RequestBody OrderUpdateDto orderUpdateDto,
                                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        orderService.updateOrder(orderId, orderUpdateDto);
        return ResponseEntity.noContent().build();
    }
}
