package bookapp.service.impl;

import bookapp.service.CartItemService;
import bookapp.repository.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public void deleteCartItemById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
