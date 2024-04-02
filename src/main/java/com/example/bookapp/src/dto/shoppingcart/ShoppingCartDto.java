package com.example.bookapp.src.dto.shoppingcart;

import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.model.User;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private User user;
    private Set<CartItem> cartItems;
}
