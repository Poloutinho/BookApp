package bookapp.service;

import bookapp.dto.shoppingcart.CartItemDto;
import bookapp.dto.shoppingcart.CartItemRequestDto;
import bookapp.dto.shoppingcart.CartItemRequestUpdateDto;
import bookapp.dto.shoppingcart.ShoppingCartDto;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartForUser(String email, Pageable pageable);

    CartItemDto save(String email, CartItemRequestDto requestDto);

    CartItemDto update(String email, Long id, CartItemRequestUpdateDto requestDto);
}
