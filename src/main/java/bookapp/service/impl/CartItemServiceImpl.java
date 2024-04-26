package bookapp.service.impl;

import bookapp.dto.shoppingcart.CartItemDto;
import bookapp.dto.shoppingcart.CartItemRequestDto;
import bookapp.dto.shoppingcart.CartItemRequestUpdateDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.EntityNotFoundException;
import bookapp.mapper.ShoppingCartMapper;
import bookapp.model.CartItem;
import bookapp.model.ShoppingCart;
import bookapp.repository.book.BookRepository;
import bookapp.repository.cartitem.CartItemRepository;
import bookapp.repository.shoppingcart.ShoppingCartRepository;
import bookapp.service.CartItemService;
import bookapp.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;
    private final BookRepository bookRepository;

    @Override
    public void deleteCartItemById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
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
