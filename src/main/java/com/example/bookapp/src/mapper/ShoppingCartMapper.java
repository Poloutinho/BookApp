package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.shoppingcart.CreateShoppingCartRequestDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toModel(CreateShoppingCartRequestDto requestDto);
}
