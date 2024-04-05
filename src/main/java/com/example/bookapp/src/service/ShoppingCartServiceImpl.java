package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.shoppingcart.CreateShoppingCartRequestDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.exception.EntityNotFoundException;
import com.example.bookapp.src.mapper.CartItemMapper;
import com.example.bookapp.src.mapper.ShoppingCartMapper;
import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.model.ShoppingCart;
import com.example.bookapp.src.repository.cartitem.CartItemRepository;
import com.example.bookapp.src.repository.shoppingcart.ShoppingCartRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public List<ShoppingCartDto> findAll(Pageable pageable) {
        return shoppingCartRepository.findAll(pageable).stream()
                .map(shoppingCartMapper::toDto)
                .toList();
    }

    @Override
    public ShoppingCartDto save(CreateShoppingCartRequestDto shoppingCartRequestDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toModel(shoppingCartRequestDto);

        Set<CartItem> cartItems = shoppingCartRequestDto.cartItems().stream()
                .collect(Collectors.toSet());

        shoppingCart.setCartItems(cartItems);

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedShoppingCart);
    }

    @Override
    public ShoppingCartDto update(Long id, ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"));

        shoppingCart.setUser(shoppingCartRepository
                .findById(shoppingCartDto.getUserId()).get().getUser());
        shoppingCart.setCartItems(shoppingCartDto.getCartItems());

        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteById(Long id) {
        shoppingCartRepository.deleteById(id);
    }
}
