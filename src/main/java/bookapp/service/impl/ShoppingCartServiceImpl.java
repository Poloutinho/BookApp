package bookapp.service.impl;

import bookapp.dto.shoppingcart.ShoppingCartDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.EntityNotFoundException;
import bookapp.mapper.ShoppingCartMapper;
import bookapp.model.ShoppingCart;
import bookapp.repository.shoppingcart.ShoppingCartRepository;
import bookapp.service.ShoppingCartService;
import bookapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;

    @Override
    public ShoppingCartDto getShoppingCartForUser(String email) {
        return shoppingCartMapper.toDto(findShoppingCartByUser(email));
    }

    private ShoppingCart findShoppingCartByUser(String email) {
        UserResponseDto responseDto = userService.getByEmail(email);
        return shoppingCartRepository.findByUserId(responseDto.getId())
                .orElseThrow(() -> new
                        EntityNotFoundException("Can`t find shopping cart by id: "
                        + responseDto.getId()));
    }
}
