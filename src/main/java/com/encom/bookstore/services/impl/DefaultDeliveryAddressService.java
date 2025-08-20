package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.DeliveryAddressRequestDto;
import com.encom.bookstore.dto.DeliveryAddressResponseDto;
import com.encom.bookstore.exceptions.DeliveryAddressAlreadyExistsException;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.DeliveryAddressMapper;
import com.encom.bookstore.model.DeliveryAddress;
import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.DeliveryAddressRepository;
import com.encom.bookstore.services.DeliveryAddressService;
import com.encom.bookstore.services.UserService;
import com.encom.bookstore.specifications.DeliveryAddressSpecifications;
import com.encom.bookstore.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultDeliveryAddressService implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    private final DeliveryAddressMapper deliveryAddressMapper;

    private final UserService userService;



    @Override
    @Transactional
    public DeliveryAddressResponseDto addAddress(DeliveryAddressRequestDto addressRequestDto) {
        DeliveryAddress addedDeliveryAddress = deliveryAddressMapper.deliveryAddressRequestDtoToAddress(addressRequestDto);
        addedDeliveryAddress = addRelatedUser(addedDeliveryAddress);

        Specification<DeliveryAddress> addressExistsSpec = createSpecificationToCheckAddressExists(addedDeliveryAddress);
        List<DeliveryAddress> existsAddresses = deliveryAddressRepository.findAll(addressExistsSpec);

        if (!existsAddresses.isEmpty()) {
            DeliveryAddress address = existsAddresses.getFirst();

            if (address.getTimeOfRemoval() == null) {
                throw new DeliveryAddressAlreadyExistsException();
            } else {
                address.setTimeOfRemoval(null);
                return deliveryAddressMapper.toDeliveryAddressResponseDto(address);
            }
        }

        addedDeliveryAddress = deliveryAddressRepository.save(addedDeliveryAddress);
        return deliveryAddressMapper.toDeliveryAddressResponseDto(addedDeliveryAddress);
    }

    @Override
    public List<DeliveryAddressResponseDto> findAllAddresses() {
        User currentUser = getCurrentUser();
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByUserAndTimeOfRemovalIsNull(currentUser);
        return deliveryAddresses.stream()
            .map(deliveryAddressMapper::toDeliveryAddressResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAddress(long addressId, DeliveryAddressRequestDto addressRequestDto) {
        DeliveryAddress address = getAddress(addressId);
        deliveryAddressMapper.updateDeliveryAddressFromDto(addressRequestDto, address);
        deliveryAddressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(long addressId) {
        DeliveryAddress address = getAddress(addressId);
        address.setTimeOfRemoval(LocalDateTime.now(ZoneOffset.UTC));
        deliveryAddressRepository.save(address);
    }

    @Override
    public DeliveryAddress getAddress(long addressId) {
        User currentUser = getCurrentUser();
        return deliveryAddressRepository.findByIdAndUser(addressId, currentUser)
            .orElseThrow(() -> new EntityNotFoundException("DeliveryAddress", Set.of(addressId)));
    }

    private DeliveryAddress addRelatedUser(DeliveryAddress deliveryAddress) {
        long userId = SecurityUtils.getCurrentUserId();
        User user = userService.getUser(userId);
        deliveryAddress.setUser(user);
        return deliveryAddress;
    }

    private Specification<DeliveryAddress> createSpecificationToCheckAddressExists(DeliveryAddress deliveryAddress) {
        Specification<DeliveryAddress> spec = Specification
            .where(DeliveryAddressSpecifications.userEquals(deliveryAddress.getUser()))
            .and(DeliveryAddressSpecifications.countryEquals(deliveryAddress.getCountry()))
            .and(DeliveryAddressSpecifications.cityEquals(deliveryAddress.getCity()))
            .and(DeliveryAddressSpecifications.postalCodeEquals(deliveryAddress.getPostalCode()))
            .and(DeliveryAddressSpecifications.streetEquals(deliveryAddress.getStreet()))
            .and(DeliveryAddressSpecifications.buildingEquals(deliveryAddress.getBuilding()));

        if (deliveryAddress.getOffice() != null && !deliveryAddress.getOffice().isBlank() ) {
            spec = spec.and(DeliveryAddressSpecifications.officeEquals(deliveryAddress.getOffice()));
        }

        return spec;
    }

    private User getCurrentUser() {
        long userId = SecurityUtils.getCurrentUserId();
        return userService.getUser(userId);
    }
}
