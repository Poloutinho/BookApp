package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
import com.example.bookapp.src.dto.order.OrderDto;
import com.example.bookapp.src.dto.order.OrderItemDto;
import com.example.bookapp.src.dto.order.OrderUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    List<OrderDto> findAll(Pageable pageable);

    OrderDto save(CreateOrderRequestDto orderRequestDto, Authentication authentication);

    OrderDto findById(Long id);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long itemId);

    OrderDto updateOrderStatus(Long orderId, OrderUpdateRequestDto status);
}
