package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.cartitem.CartItemDto;
import com.example.bookapp.src.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookapp.src.mapper.CartItemMapper;
import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.repository.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto save(CreateCartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(savedCartItem);
    }
}
