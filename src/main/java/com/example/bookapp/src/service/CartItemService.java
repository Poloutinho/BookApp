package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.cartitem.CartItemDto;
import com.example.bookapp.src.dto.cartitem.CreateCartItemRequestDto;

public interface CartItemService {
    CartItemDto save(CreateCartItemRequestDto categoryDto);
}
