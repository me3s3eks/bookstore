package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.DeliveryAddressRequestDto;
import com.encom.bookstore.dto.DeliveryAddressResponseDto;
import com.encom.bookstore.model.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAddressMapper {

    DeliveryAddress deliveryAddressRequestDtoToAddress(DeliveryAddressRequestDto deliveryAddressRequestDto);

    DeliveryAddressResponseDto toDeliveryAddressResponseDto(DeliveryAddress deliveryAddress);

    DeliveryAddress updateDeliveryAddressFromDto(DeliveryAddressRequestDto deliveryAddressRequestDto,
                                                 @MappingTarget DeliveryAddress deliveryAddress);
}
