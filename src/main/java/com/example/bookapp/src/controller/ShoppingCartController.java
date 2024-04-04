package com.example.bookapp.src.controller;

import com.example.bookapp.src.dto.cartitem.CartItemDto;
import com.example.bookapp.src.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.service.CartItemService;
import com.example.bookapp.src.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Operation(summary = "Add new shopping cart", description = "Add new shopping cart")
    public CartItemDto createShoppingCart(
            @RequestBody CreateCartItemRequestDto cartItemRequestDto) {
        return cartItemService.save(cartItemRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all shopping carts", description = "Get all shopping carts")
    @GetMapping
    public List<ShoppingCartDto> getAll(Pageable pageable) {
        return shoppingCartService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update shopping cart by Id", description = "Update shopping cart by Id")
    public CartItemDto updateShoppingCart(
            @PathVariable Long cartItemId, @RequestBody CartItemDto cartItemDto) {
        return cartItemService.update(cartItemId, cartItemDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete shopping cart by Id", description = "Delete shopping cart by Id")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteById(cartItemId);
    }
}
