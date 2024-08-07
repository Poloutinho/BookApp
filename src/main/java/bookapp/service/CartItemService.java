package bookapp.service;

import bookapp.dto.shoppingcart.CartItemDto;
import bookapp.dto.shoppingcart.CartItemRequestDto;
import bookapp.dto.shoppingcart.CartItemRequestUpdateDto;

public interface CartItemService {
    void deleteCartItemById(Long cartItemId);

    CartItemDto save(String email, CartItemRequestDto requestDto);

    CartItemDto update(String email, Long id, CartItemRequestUpdateDto requestDto);
}
