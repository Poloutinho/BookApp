package com.example.bookapp.src.repository.shoppingcart;

import com.example.bookapp.src.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
