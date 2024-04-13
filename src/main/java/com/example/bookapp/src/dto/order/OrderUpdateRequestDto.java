package com.example.bookapp.src.dto.order;

import com.example.bookapp.src.model.Order;

public record OrderUpdateRequestDto(
        Order.Status status
) {
}
