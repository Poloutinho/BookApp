package com.example.bookapp.src.dto.cartitem;

import jakarta.validation.constraints.NotNull;

public record CreateCartItemRequestDto(@NotNull Long bookId,
                                       Long quantity
) {
}
