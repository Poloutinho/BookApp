package com.example.bookapp.src.controller;

import com.example.bookapp.src.dto.shoppingcart.CartItemDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestDto;
import com.example.bookapp.src.dto.shoppingcart.CartItemRequestUpdateDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.service.CartItemService;
import com.example.bookapp.src.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CartItemService cartItemService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Add new shopping cart",
            description = "Add new shopping cart")
    public CartItemDto saveCartItem(Authentication authentication,
                                    @RequestBody CartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.save(user.getEmail(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "Get shopping cart for authenticated user",
            description = "Get shopping cart for authenticated user")
    @GetMapping
    public ShoppingCartDto getShoppingCart(Authentication authentication, Pageable pageable) {
        User user = (User)authentication.getPrincipal();
        return shoppingCartService.getShoppingCartForUser(user.getEmail(), pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update shopping cart by Id",
            description = "Update shopping cart by Id")
    public CartItemDto updateShoppingCart(Authentication authentication,
                                          @PathVariable Long cartItemId,
                                          @RequestBody CartItemRequestUpdateDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.update(user.getEmail(), cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete cart item",
            description = "Delete cart item from the shopping cart")
    public void deleteCartItemFromShoppingCart(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItemById(cartItemId);
    }
}