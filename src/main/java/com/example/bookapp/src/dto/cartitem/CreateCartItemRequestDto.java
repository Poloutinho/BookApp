package com.example.bookapp.src.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull private Long shoppingCartId;
    @NotNull private Long bookId;
    private Long quantity;
}
