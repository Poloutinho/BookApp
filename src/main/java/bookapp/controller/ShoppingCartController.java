package bookapp.controller;

import bookapp.dto.shoppingcart.CartItemDto;
import bookapp.dto.shoppingcart.CartItemRequestDto;
import bookapp.dto.shoppingcart.CartItemRequestUpdateDto;
import bookapp.dto.shoppingcart.ShoppingCartDto;
import bookapp.model.User;
import bookapp.service.CartItemService;
import bookapp.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new cart item",
            description = "Add new cart item")
    public CartItemDto saveCartItem(Authentication authentication,
                                    @RequestBody CartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartItemService.save(user.getEmail(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "Get shopping cart for authenticated user",
            description = "Get shopping cart for authenticated user")
    @GetMapping
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return shoppingCartService.getShoppingCartForUser(user.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update cart item by Id",
            description = "Update cart item by Id")
    public CartItemDto updateShoppingCart(Authentication authentication,
                                          @PathVariable Long cartItemId,
                                          @RequestBody CartItemRequestUpdateDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return cartItemService.update(user.getEmail(), cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete cart item",
            description = "Delete cart item from the shopping cart")
    public void deleteCartItemFromShoppingCart(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItemById(cartItemId);
    }
}
