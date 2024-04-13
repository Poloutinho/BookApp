package com.example.bookapp.src.service.impl;

import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
import com.example.bookapp.src.dto.order.OrderDto;
import com.example.bookapp.src.dto.order.OrderItemDto;
import com.example.bookapp.src.dto.order.OrderUpdateRequestDto;
import com.example.bookapp.src.exception.EntityNotFoundException;
import com.example.bookapp.src.mapper.OrderMapper;
import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.model.Order;
import com.example.bookapp.src.model.OrderItem;
import com.example.bookapp.src.model.ShoppingCart;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.order.OrderRepository;
import com.example.bookapp.src.repository.orderitem.OrderItemRepository;
import com.example.bookapp.src.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookapp.src.service.OrderService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto save(CreateOrderRequestDto orderRequestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find shopping cart"));
        Order order = new Order(shoppingCart);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        orderRepository.save(order);
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem(cartItem);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        OrderDto orderDto = findById(orderId);
        return orderDto.orderItems().stream()
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        OrderDto orderDto = findById(orderId);
        OrderItemDto orderItemDto = orderDto.orderItems().stream()
                .filter(item -> item.id().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));
        return orderItemDto;
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find cart item by id: " + orderId)
                );
        orderMapper.update(order, requestDto);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
