package com.example.bookapp.src.dto.shoppingcart;

import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.model.User;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CreateShoppingCartRequestDto(@NotNull User user,
                                           Set<CartItem> cartItems
) {
}
