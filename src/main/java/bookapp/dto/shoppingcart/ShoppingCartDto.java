package bookapp.dto.shoppingcart;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
