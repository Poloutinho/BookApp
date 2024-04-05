package com.example.bookapp.src.controller;

import com.example.bookapp.src.dto.cartitem.CartItemDto;
import com.example.bookapp.src.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookapp.src.dto.shoppingcart.CreateShoppingCartRequestDto;
import com.example.bookapp.src.dto.shoppingcart.ShoppingCartDto;
import com.example.bookapp.src.mapper.CartItemMapper;
import com.example.bookapp.src.model.ShoppingCart;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookapp.src.repository.user.UserRepository;
import com.example.bookapp.src.service.CartItemService;
import com.example.bookapp.src.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartItemMapper cartItemMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Add new shopping cart", description = "Add new shopping cart")
    public CartItemDto createCartItem(
            @RequestBody CreateCartItemRequestDto cartItemRequestDto,
            Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getId())
                        .orElseGet(() -> {
                            ShoppingCart newShoppingCart = new ShoppingCart();
                            newShoppingCart.setUser(user);
                            newShoppingCart.setId(user.getId());
                            shoppingCartRepository.save(newShoppingCart);
                            return newShoppingCart;
                        });
        cartItemRequestDto.setShoppingCartId(shoppingCart.getId());
        CartItemDto cartItemDto = cartItemService.save(cartItemRequestDto);
        return cartItemDto;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add new shopping cart", description = "Add new shopping cart")
    @PostMapping("/shoppingCart")
    public ShoppingCartDto createShoppingCart(
            @RequestBody CreateShoppingCartRequestDto shoppingCartRequestDto) {
        return shoppingCartService.save(shoppingCartRequestDto);
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
