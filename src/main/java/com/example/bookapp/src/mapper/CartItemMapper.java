package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.cartitem.CartItemDto;
import com.example.bookapp.src.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookapp.src.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    CartItemDto toDto(CartItem shoppingCart);

    CartItem toModel(CreateCartItemRequestDto requestDto);
}
