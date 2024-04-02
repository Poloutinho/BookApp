package com.example.bookapp.src.repository.cartitem;

import com.example.bookapp.src.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
