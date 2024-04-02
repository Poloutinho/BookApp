package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.shoppingcart.CreateShoppingCartRequestDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    List<ShoppingCartDto> findAll(Pageable pageable);

    ShoppingCartDto save(CreateShoppingCartRequestDto shoppingCartRequestDto);

    ShoppingCartDto update(Long id, ShoppingCartDto shoppingCartDto);

    void deleteById(Long id);
}
