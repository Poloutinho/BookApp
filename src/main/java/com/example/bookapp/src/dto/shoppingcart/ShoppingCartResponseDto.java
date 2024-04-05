package com.example.bookapp.src.dto.shoppingcart;

import com.example.bookapp.src.dto.cartitem.CartItemDto;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
