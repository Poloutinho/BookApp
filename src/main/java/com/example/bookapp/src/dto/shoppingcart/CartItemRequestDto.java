package com.example.bookapp.src.dto.shoppingcart;

public record CartItemRequestDto(
        Long bookId,
        int quantity
) {
}
