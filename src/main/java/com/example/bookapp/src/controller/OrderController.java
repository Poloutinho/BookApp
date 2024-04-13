package com.example.bookapp.src.controller;

import com.example.bookapp.src.dto.order.CreateOrderRequestDto;
import com.example.bookapp.src.dto.order.OrderDto;
import com.example.bookapp.src.dto.order.OrderItemDto;
import com.example.bookapp.src.dto.order.OrderUpdateRequestDto;
import com.example.bookapp.src.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add new order", description = "Add new order")
    public OrderDto createOrder(@RequestBody CreateOrderRequestDto orderRequestDto,
                    Authentication authentication) {
        return orderService.save(orderRequestDto, authentication);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "Get all history of orders", description = "Get all history of orders")
    @GetMapping
    public List<OrderDto> getAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Find items of order by Id", description = "Find items of order by Id")
    public List<OrderItemDto> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "Find item by orderId and itemId",
                    description = "Find item by orderId and itemId")
    public OrderItemDto getOrderItemById(@PathVariable Long orderId, @PathVariable Long id) {
        return orderService.getOrderItem(orderId, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    @Operation(summary = "Update status of order by orderId",
                    description = "Update status of order by orderId")
    public OrderDto updateOrderStatus(@PathVariable Long id,
                                      @RequestBody OrderUpdateRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
