package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.OrderDto;
import com.encom.bookstore.dto.OrderFilterDto;
import com.encom.bookstore.dto.OrderRequestDto;
import com.encom.bookstore.dto.OrderResponseDto;
import com.encom.bookstore.dto.OrderUpdateDto;
import com.encom.bookstore.dto.OrderedBookResponseDto;
import com.encom.bookstore.exceptions.EmptyShoppingCartException;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.InvalidRequestDataException;
import com.encom.bookstore.exceptions.UnavailablePaymentMethodException;
import com.encom.bookstore.mappers.OrderMapper;
import com.encom.bookstore.mappers.OrderedBookMapper;
import com.encom.bookstore.model.DeliveryAddress;
import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.OrderedBook;
import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.PaymentStatus;
import com.encom.bookstore.model.ReceivingMethod;
import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.OrderRepository;
import com.encom.bookstore.security.UserRole;
import com.encom.bookstore.services.DeliveryAddressService;
import com.encom.bookstore.services.OrderService;
import com.encom.bookstore.services.OrderedBookService;
import com.encom.bookstore.services.ShoppingCartService;
import com.encom.bookstore.services.UserService;
import com.encom.bookstore.sessions.ShoppingCart;
import com.encom.bookstore.specifications.OrderSpecifications;
import com.encom.bookstore.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final ShoppingCartService shoppingCartService;

    private final OrderMapper orderMapper;

    private final UserService userService;

    private final DeliveryAddressService deliveryAddressService;

    private final OrderRepository orderRepository;

    private final OrderedBookService orderedBookService;

    private final OrderedBookMapper orderedBookMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        ShoppingCart shoppingCart = shoppingCartService.retrieveShoppingCart();

        if (shoppingCart.isEmpty()) {
            throw new EmptyShoppingCartException();
        }

        checkPaymentMethodAvailability(orderRequestDto);

        Order order = orderMapper.orderRequestDtoToOrder(orderRequestDto);
        addRelatedEntities(order, orderRequestDto);
        setInitialState(order);
        orderRepository.save(order);

        orderedBookService.addBooksToOrder(order, shoppingCart);

        return orderMapper.orderToOrderResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> findOrdersByFilterDto(Pageable pageable, OrderFilterDto orderFilterDto) {
        Specification<Order> orderSpec = parseFilterDtoToSpecification(orderFilterDto);
        Page<Order> ordersPage = orderRepository.findAll(orderSpec, pageable);
        return ordersPage.map(orderMapper::orderToOrderResponseDto);
    }

    @Override
    public OrderDto findOrder(long orderId) {
        Order order;
        if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
            order = getOrderWithCheckBelongsToUser(orderId);
        } else {
            order = getOrder(orderId);
        }

        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        BigDecimal orderTotalPrice = getTotalPrice(order);
        orderDto.setTotalPrice(orderTotalPrice);
        return orderDto;
    }

    @Override
    public Order getOrder(long orderId) {
        return orderRepository.findWithAllRelatedEntitiesById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Order", Set.of(orderId)));
    }

    @Override
    public List<OrderedBookResponseDto> findOrderedBooksForOrder(long orderId) {
        Order order;
        if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
            order = getOrderWithCheckBelongsToUser(orderId);
        } else {
            order = getOrder(orderId);
        }

        List<OrderedBook> orderedBooks = orderedBookService.getOrderedBooksForOrder(order);
        return orderedBooks.stream()
            .map(orderedBookMapper::toOrderedBookResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public void updateOrder(long orderId, OrderUpdateDto orderUpdateDto) {
        Order order;
        if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
            order = getOrderWithCheckBelongsToUser(orderId);
            if (orderUpdateDto.cancelled() != null && orderUpdateDto.cancelled()) {
                order.setCancelled(orderUpdateDto.cancelled());
            }
        } else {
            order = getOrder(orderId);
            if(order.isCancelled() == true && orderUpdateDto.cancelled() != null && !orderUpdateDto.cancelled()) {
                throw new InvalidRequestDataException("cancelled", "Cancelled order can't be restore");
            }
            orderMapper.updateOrderFromDto(orderUpdateDto, order);
            if (orderUpdateDto.orderStatus() != null) {
                order.setTimeOfStatusChanged(LocalDateTime.now(ZoneOffset.UTC));
            }
        }
        orderRepository.save(order);
    }

    @Override
    public BigDecimal getTotalPrice(Order order) {
        return orderedBookService.getOrderedBooksForOrder(order).stream()
            .map(book -> book.getPrice().multiply(BigDecimal.valueOf(book.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void checkPaymentMethodAvailability(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.paymentMethod() == PaymentMethod.CASH
            && orderRequestDto.receivingMethod() == ReceivingMethod.PICKUP_FROM_POST_OFFICE) {
            throw new UnavailablePaymentMethodException(orderRequestDto.receivingMethod());
        }
    }

    private Order addRelatedEntities(Order order, OrderRequestDto orderRequestDto) {
        long currentUserId = SecurityUtils.getCurrentUserId();
        User currentUser = userService.getUser(currentUserId);
        order.setUser(currentUser);

        DeliveryAddress deliveryAddress = deliveryAddressService.getAddress(orderRequestDto.deliveryAddressId());
        order.setDeliveryAddress(deliveryAddress);

        return order;
    }

    private Order setInitialState(Order order) {
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        order.setCancelled(false);
        order.setTimeOfStatusChanged(LocalDateTime.now(ZoneOffset.UTC));

        return order;
    }

    private Specification<Order> parseFilterDtoToSpecification(OrderFilterDto orderFilterDto) {
        Specification<Order> spec = Specification
            .where((root, query, cb) -> cb.conjunction());

        if (orderFilterDto == null) {
            if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
                spec = spec.and(OrderSpecifications.withUser(SecurityUtils.getCurrentUserId()));
            }
            return spec;
        }

        if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
            spec = spec.and(OrderSpecifications.withUser(SecurityUtils.getCurrentUserId()));
        } else {
            if (orderFilterDto.userId() != null) {
                spec = spec.and(OrderSpecifications.withUser(orderFilterDto.userId()));
            }
        }

        if (orderFilterDto.postalCodes() != null && !orderFilterDto.postalCodes().isEmpty()) {
            spec = spec.and(OrderSpecifications.withPostalCodeIn(orderFilterDto.postalCodes()));
        }

        if (orderFilterDto.paymentMethod() != null) {
            spec = spec.and(OrderSpecifications.byPaymentMethod(orderFilterDto.paymentMethod()));
        }

        if (orderFilterDto.receivingMethod() != null) {
            spec = spec.and(OrderSpecifications.byReceivingMethod(orderFilterDto.receivingMethod()));
        }

        if (orderFilterDto.paymentStatus() != null) {
            spec = spec.and(OrderSpecifications.byPaymentStatus(orderFilterDto.paymentStatus()));
        }

        if (orderFilterDto.orderStatus() != null) {
            spec = spec.and(OrderSpecifications.byOrderStatus(orderFilterDto.orderStatus()));
        }

        if (SecurityUtils.userHasAnyRole(Set.of(UserRole.ROLE_MANAGER.name(), UserRole.ROLE_ADMIN.name()))) {
            if (orderFilterDto.timeOfStatusChangedBefore() != null) {
                spec = spec.and(OrderSpecifications.timeOfStatusChangedOnOrBefore(orderFilterDto.timeOfStatusChangedBefore()));
            }
        }

        if (SecurityUtils.userHasAnyRole(Set.of(UserRole.ROLE_MANAGER.name(), UserRole.ROLE_ADMIN.name()))) {
            if (orderFilterDto.timeOfStatusChangedAfter() != null) {
                spec = spec.and(OrderSpecifications.timeOfStatusChangedOnOrAfter(orderFilterDto.timeOfStatusChangedAfter()));
            }
        }

        if (orderFilterDto.express() != null) {
            spec = spec.and(OrderSpecifications.byExpress(orderFilterDto.express()));
        }

        if (orderFilterDto.cancelled() != null) {
            spec = spec.and(OrderSpecifications.byCancelled(orderFilterDto.cancelled()));
        }

        return spec;
    }

    private User getCurrentUser() {
        long userId = SecurityUtils.getCurrentUserId();
        return userService.getUser(userId);
    }

    private Order getOrderWithCheckBelongsToUser(long orderId) {
        User user = getCurrentUser();
        return orderRepository.findWithAllRelatedEntitiesByIdAndUser(orderId, user)
            .orElseThrow(() -> new EntityNotFoundException("Order", Set.of(orderId)));
    }
}
