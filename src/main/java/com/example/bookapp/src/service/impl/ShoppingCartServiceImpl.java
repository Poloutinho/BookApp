package com.example.bookapp.src.service.impl;

import com.example.bookapp.src.dto.shoppingcart.CartItemDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestUpdateDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.EntityNotFoundException;
import com.example.bookapp.src.mapper.ShoppingCartMapper;
import com.example.bookapp.src.model.CartItem;
import com.example.bookapp.src.model.ShoppingCart;
import com.example.bookapp.src.repository.book.BookRepository;
import com.example.bookapp.src.repository.cartitem.CartItemRepository;
import com.example.bookapp.src.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookapp.src.service.ShoppingCartService;
import com.example.bookapp.src.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCartForUser(String email, Pageable pageable) {
        return shoppingCartMapper.toDto(findShoppingCartByUser(email));
    }

    @Override
    public CartItemDto save(String email, CartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = findShoppingCartByUser(email);
        CartItem cartItem = shoppingCartMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);

        cartItem.setBook(bookRepository.findById(cartItem.getBook().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find book with id" + cartItem.getBook().getId())
                ));

        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto update(String email, Long id, CartItemRequestUpdateDto requestDto) {
        CartItem cartItem = findShoppingCartByUser(email).getCartItems().stream()
                .filter(item -> Objects.equals(id, item.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find cart item by id: " + id)
                );
        shoppingCartMapper.update(cartItem, requestDto);
        return shoppingCartMapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    private ShoppingCart findShoppingCartByUser(String email) {
        UserResponseDto responseDto = userService.getByEmail(email);
        return shoppingCartRepository.findByUserId(responseDto.getId())
                .orElseThrow(() -> new RuntimeException("Can't find shopping cart"));
    }
}
