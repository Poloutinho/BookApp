package com.example.bookapp.src.dto.shoppingcart;

import com.example.bookapp.src.model.CartItem;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
