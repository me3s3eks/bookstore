package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.OrderDto;
import com.encom.bookstore.dto.OrderRequestDto;
import com.encom.bookstore.dto.OrderResponseDto;
import com.encom.bookstore.dto.OrderUpdateDto;
import com.encom.bookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto orderToOrderResponseDto(Order order);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "deliveryAddressId", source = "deliveryAddress.id")
    OrderDto orderToOrderDto(Order order);

    Order updateOrderFromDto(OrderUpdateDto orderUpdateDto, @MappingTarget Order order);
}
