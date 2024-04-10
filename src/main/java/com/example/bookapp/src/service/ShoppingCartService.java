package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.shoppingcart.CartItemDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestUpdateDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartForUser(String email, Pageable pageable);

    CartItemDto save(String email, CartItemRequestDto requestDto);

    CartItemDto update(String email, Long id, CartItemRequestUpdateDto requestDto);
}
