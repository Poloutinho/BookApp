package com.example.bookapp.src.dto.shoppingcart;

import com.example.bookapp.src.model.CartItem;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CreateShoppingCartRequestDto(@NotNull Long userId,
                                           Set<CartItem> cartItems
) {
}
