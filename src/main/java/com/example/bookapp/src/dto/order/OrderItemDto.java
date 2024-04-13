package com.example.bookapp.src.dto.order;

public record OrderItemDto(
        Long id,
        Long bookId,
        Long quantity
) {

}
